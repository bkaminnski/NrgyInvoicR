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
        PlanVersion planVersion = findPlanVersion(client, onDate);
        Bucket buckets = createBuckets(planVersion);
        List<ReadingValue> readingValues = findReadingValuesToInvoice(invoiceRun, client);
        List<FlattenedBucket> flattenedBuckets = putReadingValuesToBuckets(buckets, readingValues);
        List<InvoiceLine> invoiceLines = createInvoiceLines(planVersion, flattenedBuckets);
        BigDecimal invoiceGrossTotal = calculateInvoiceGrossTotal(invoiceLines);
        Invoice savedInvoice = saveInvoice(invoiceRun, client, invoiceNumber, invoiceGrossTotal);
        saveInvoiceLines(invoiceLines, savedInvoice);
    }

    private PlanVersion findPlanVersion(Client client, ZonedDateTime onDate) throws NoPlanValidOnDate, NoPlanVersionValidOnDate {
        ClientPlanAssignment clientPlanAssignment = clientPlanAssignmentsRepository
                .findFirstByClientIdAndValidSinceLessThanEqualOrderByValidSinceAscIdDesc(client.getId(), onDate)
                .orElseThrow(() -> new NoPlanValidOnDate(client, onDate));
        Plan plan = clientPlanAssignment.getPlan();
        return plan
                .getVersionValidOn(onDate)
                .orElseThrow(() -> new NoPlanVersionValidOnDate(client, plan, onDate));
    }

    private Bucket createBuckets(PlanVersion planVersion) throws IOException {
        return new ExpressionParser().parse(planVersion.getExpression());
    }

    private List<ReadingValue> findReadingValuesToInvoice(InvoiceRun invoiceRun, Client client) throws NoReadingValueFound {
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
        return readingValues;
    }

    private List<FlattenedBucket> putReadingValuesToBuckets(Bucket buckets, List<ReadingValue> readingValues) {
        ReadingValue readingValue = readingValues.get(0);
        for (int i = 1; i < readingValues.size(); i++) {
            if (readingValues.get(i).getDate().isAfter(readingValue.getDate())) {
                buckets.accept(readingValue);
            }
            readingValue = readingValues.get(i);
        }
        buckets.accept(readingValues.get(readingValues.size() - 1));
        return buckets.flatten();
    }

    private List<InvoiceLine> createInvoiceLines(PlanVersion planVersion, List<FlattenedBucket> flattenedBuckets) {
        List<InvoiceLine> invoiceLines = new ArrayList<>();
        for (FlattenedBucket flattenedBucket : flattenedBuckets) {
            invoiceLines.add(new InvoiceLine(flattenedBucket.getDescription(), flattenedBucket.getUnitPrice(), flattenedBucket.getTotalUsage(), KWH, nrgyInvoicRConfig.getVatAsBigDecimal()));
        }
        invoiceLines.add(new InvoiceLine("Network fee", planVersion.getFixedFees().getNetworkFee(), ONE, NONE, nrgyInvoicRConfig.getVatAsBigDecimal()));
        invoiceLines.add(new InvoiceLine("Subscription fee", planVersion.getFixedFees().getSubscriptionFee(), ONE, NONE, nrgyInvoicRConfig.getVatAsBigDecimal()));
        return invoiceLines;
    }

    private BigDecimal calculateInvoiceGrossTotal(List<InvoiceLine> invoiceLines) {
        return invoiceLines.stream().map(InvoiceLine::getGrossTotal).reduce(ZERO, BigDecimal::add);
    }

    private Invoice saveInvoice(InvoiceRun invoiceRun, Client client, Integer invoiceNumber, BigDecimal invoiceGrossTotal) {
        Invoice invoice = new Invoice(format(nrgyInvoicRConfig.getInvoiceNumberTemplate(), invoiceNumber), invoiceRun.getIssueDate(), invoiceRun.getId(), client, invoiceGrossTotal);
        return invoicesRepository.save(invoice);
    }

    private void saveInvoiceLines(List<InvoiceLine> invoiceLines, Invoice savedInvoice) {
        for (InvoiceLine invoiceLine : invoiceLines) {
            invoiceLine.setInvoiceId(savedInvoice.getId());
        }
        invoiceLinesRepository.saveAll(invoiceLines);
    }
}
