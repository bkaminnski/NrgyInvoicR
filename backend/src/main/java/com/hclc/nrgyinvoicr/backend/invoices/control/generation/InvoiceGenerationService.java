package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.NrgyInvoicRConfig;
import com.hclc.nrgyinvoicr.backend.clients.control.ClientPlanAssignmentsRepository;
import com.hclc.nrgyinvoicr.backend.clients.control.ClientsRepository;
import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.clients.entity.ClientPlanAssignment;
import com.hclc.nrgyinvoicr.backend.invoices.control.InvoiceLinesRepository;
import com.hclc.nrgyinvoicr.backend.invoices.control.InvoicesRepository;
import com.hclc.nrgyinvoicr.backend.invoices.entity.Invoice;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceLine;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.ExpressionParser;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.Bucket;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.FlattenedBucket;
import com.hclc.nrgyinvoicr.backend.plans.entity.Plan;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersion;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingValuesRepository;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingsRepository;
import com.hclc.nrgyinvoicr.backend.readings.entity.Reading;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hclc.nrgyinvoicr.backend.invoices.entity.Unit.KWH;
import static com.hclc.nrgyinvoicr.backend.invoices.entity.Unit.NONE;
import static java.lang.String.format;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

@Component
public class InvoiceGenerationService {
    private final NrgyInvoicRConfig nrgyInvoicRConfig;
    private final ClientsRepository clientsRepository;
    private final ReadingsRepository readingsRepository;
    private final ReadingValuesRepository readingValuesRepository;
    private final ClientPlanAssignmentsRepository clientPlanAssignmentsRepository;
    private final InvoicesRepository invoicesRepository;
    private final InvoiceLinesRepository invoiceLinesRepository;

    public InvoiceGenerationService(NrgyInvoicRConfig nrgyInvoicRConfig, ClientsRepository clientsRepository, ReadingsRepository readingsRepository, ReadingValuesRepository readingValuesRepository, ClientPlanAssignmentsRepository clientPlanAssignmentsRepository, InvoicesRepository invoicesRepository, InvoiceLinesRepository invoiceLinesRepository) {
        this.nrgyInvoicRConfig = nrgyInvoicRConfig;
        this.clientsRepository = clientsRepository;
        this.readingsRepository = readingsRepository;
        this.readingValuesRepository = readingValuesRepository;
        this.clientPlanAssignmentsRepository = clientPlanAssignmentsRepository;
        this.invoicesRepository = invoicesRepository;
        this.invoiceLinesRepository = invoiceLinesRepository;
    }

    public void start(InvoiceRun invoiceRun) throws IOException, NoReadingValueFound, NoPlanValidOnDate, NoPlanVersionValidOnDate {
        List<Client> clients = clientsRepository.findAll();
        int invoiceNumber = invoiceRun.getFirstInvoiceNumber();
        for (Client client : clients) {
            generateInvoiceForClient(invoiceRun, client, invoiceNumber);
            invoiceNumber++;
        }
    }

    private void generateInvoiceForClient(InvoiceRun invoiceRun, Client client, Integer invoiceNumber) throws IOException, NoPlanVersionValidOnDate, NoPlanValidOnDate, NoReadingValueFound {
        ZonedDateTime onDate = invoiceRun.getSinceClosed();

        // find plan version
        ClientPlanAssignment clientPlanAssignment = clientPlanAssignmentsRepository
                .findFirstByClientIdAndValidSinceLessThanEqualOrderByValidSinceAscIdDesc(client.getId(), onDate)
                .orElseThrow(() -> new NoPlanValidOnDate(client, onDate));
        Plan plan = clientPlanAssignment.getPlan();
        PlanVersion planVersion = plan
                .getVersionValidOn(onDate)
                .orElseThrow(() -> new NoPlanVersionValidOnDate(client, plan, onDate));

        // create buckets
        Bucket buckets = new ExpressionParser().parse(planVersion.getExpression());

        // find reading values to invoice
        List<Reading> readings = readingsRepository
                .findByReadingSpreadSinceClosedLessThanAndReadingSpreadUntilOpenGreaterThanAndMeter(
                        invoiceRun.getUntilOpen(),
                        invoiceRun.getSinceClosed(),
                        client.getMeter()
                );
        List<Long> readingsIds = readings.stream().map(Reading::getId).collect(toList());
        List<ReadingValue> readingValues = readingValuesRepository
                .findByReadingIdInAndDateGreaterThanEqualAndDateLessThanOrderByDateAscReadingIdAsc(
                        readingsIds,
                        invoiceRun.getSinceClosed(),
                        invoiceRun.getUntilOpen()
                );
        if (readingValues.isEmpty()) {
            throw new NoReadingValueFound(client);
        }

        // put reading values to buckets
        ReadingValue readingValue = readingValues.get(0);
        for (int i = 1; i < readingValues.size(); i++) {
            if (readingValues.get(i).getDate().isAfter(readingValue.getDate())) {
                buckets.accept(readingValue);
            }
            readingValue = readingValues.get(i);
        }
        buckets.accept(readingValues.get(readingValues.size() - 1));
        List<FlattenedBucket> flattenedBuckets = buckets.flatten();

        // create invoice lines
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        for (FlattenedBucket flattenedBucket : flattenedBuckets) {
            invoiceLines.add(new InvoiceLine(flattenedBucket.getDescription(), flattenedBucket.getUnitPrice(), flattenedBucket.getTotalUsage(), KWH, nrgyInvoicRConfig.getVatAsBigDecimal()));
        }
        invoiceLines.add(new InvoiceLine("Network fee", planVersion.getFixedFees().getNetworkFee(), ONE, NONE, nrgyInvoicRConfig.getVatAsBigDecimal()));
        invoiceLines.add(new InvoiceLine("Subscription fee", planVersion.getFixedFees().getSubscriptionFee(), ONE, NONE, nrgyInvoicRConfig.getVatAsBigDecimal()));

        // calculate invoice gross total
        BigDecimal invoiceGrossTotal = invoiceLines.stream().map(InvoiceLine::getGrossTotal).reduce(ZERO, BigDecimal::add);

        // save invoice
        Invoice invoice = new Invoice(format(nrgyInvoicRConfig.getInvoiceNumberTemplate(), invoiceNumber), invoiceRun.getIssueDate(), invoiceRun.getId(), client, invoiceGrossTotal);
        Invoice savedInvoice = invoicesRepository.save(invoice);

        // save invoice lines
        for (InvoiceLine invoiceLine : invoiceLines) {
            invoiceLine.setInvoiceId(savedInvoice.getId());
        }
        invoiceLinesRepository.saveAll(invoiceLines);
    }
}
