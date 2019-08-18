package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.NrgyInvoicRConfig;
import com.hclc.nrgyinvoicr.backend.clients.control.ClientPlanAssignmentsService;
import com.hclc.nrgyinvoicr.backend.clients.control.NoPlanValidOnDate;
import com.hclc.nrgyinvoicr.backend.clients.control.NoPlanVersionValidOnDate;
import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.invoices.control.InvoiceLinesRepository;
import com.hclc.nrgyinvoicr.backend.invoices.control.InvoiceRunsRepository;
import com.hclc.nrgyinvoicr.backend.invoices.control.InvoicesRepository;
import com.hclc.nrgyinvoicr.backend.invoices.entity.Invoice;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceLine;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoicePrintoutGenerationDescriptor;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.ExpressionParser;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.Bucket;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.FlattenedBucket;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersion;
import com.hclc.nrgyinvoicr.backend.readings.control.NoReadingValueFound;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingValuesService;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.hclc.nrgyinvoicr.backend.invoices.entity.Unit.KWH;
import static com.hclc.nrgyinvoicr.backend.invoices.entity.Unit.NONE;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
class InvoiceGenerator {
    private final ProgressTracker progressTracker;
    private final NrgyInvoicRConfig nrgyInvoicRConfig;
    private final ClientPlanAssignmentsService clientPlanAssignmentsService;
    private final ReadingValuesService readingValuesService;
    private final InvoiceRunsRepository invoiceRunsRepository;
    private final InvoicesRepository invoicesRepository;
    private final InvoiceLinesRepository invoiceLinesRepository;
    private final InvoicePrintoutGenerator invoicePrintoutGenerator;

    InvoiceGenerator(ProgressTracker progressTracker, NrgyInvoicRConfig nrgyInvoicRConfig, ClientPlanAssignmentsService clientPlanAssignmentsService, ReadingValuesService readingValuesService, InvoiceRunsRepository invoiceRunsRepository, InvoicesRepository invoicesRepository, InvoiceLinesRepository invoiceLinesRepository, InvoicePrintoutGenerator invoicePrintoutGenerator) {
        this.progressTracker = progressTracker;
        this.nrgyInvoicRConfig = nrgyInvoicRConfig;
        this.clientPlanAssignmentsService = clientPlanAssignmentsService;
        this.readingValuesService = readingValuesService;
        this.invoiceRunsRepository = invoiceRunsRepository;
        this.invoicesRepository = invoicesRepository;
        this.invoiceLinesRepository = invoiceLinesRepository;
        this.invoicePrintoutGenerator = invoicePrintoutGenerator;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public void generateInvoice(InvoicePrintoutGenerationDescriptor descriptor, InvoiceRun invoiceRun, int invoiceNumber, Client client) {
        try {
            tryGeneratingInvoice(descriptor, invoiceRun, client, invoiceNumber);
            invoiceRun = progressTracker.incrementSuccesses(invoiceRun);
        } catch (NoPlanVersionValidOnDate | NoPlanValidOnDate | NoReadingValueFound | ErrorGeneratingPdfPrintoutOfAnInvoice e) {
            progressTracker.addMessage(invoiceRun, e.getMessage());
            invoiceRun = progressTracker.incrementFailures(invoiceRun);
        } catch (Exception e) {
            progressTracker.addMessage(invoiceRun, "Unknown error occurred: " + e.getMessage());
            invoiceRun = progressTracker.incrementFailures(invoiceRun);
        } finally {
            invoiceRunsRepository.save(invoiceRun);
        }
    }

    private void tryGeneratingInvoice(InvoicePrintoutGenerationDescriptor descriptor, InvoiceRun invoiceRun, Client client, Integer invoiceNumber) throws IOException, NoPlanVersionValidOnDate, NoPlanValidOnDate, NoReadingValueFound, ErrorGeneratingPdfPrintoutOfAnInvoice {
        ZonedDateTime onDate = invoiceRun.getSinceClosed();
        PlanVersion planVersion = findPlanVersion(client, onDate);
        Bucket buckets = createBuckets(planVersion);
        List<ReadingValue> readingValues = findReadingValuesToInvoice(invoiceRun, client);
        List<FlattenedBucket> flattenedBuckets = putReadingValuesToBuckets(buckets, readingValues);
        List<InvoiceLine> invoiceLines = createInvoiceLines(planVersion, flattenedBuckets);
        BigDecimal invoiceGrossTotal = calculateInvoiceGrossTotal(invoiceLines);
        Invoice savedInvoice = saveInvoice(invoiceRun, client, invoiceNumber, invoiceGrossTotal);
        saveInvoiceLines(invoiceLines, savedInvoice);
        printToPdf(descriptor, invoiceLines, savedInvoice, client);
    }

    private PlanVersion findPlanVersion(Client client, ZonedDateTime onDate) throws NoPlanVersionValidOnDate, NoPlanValidOnDate {
        return clientPlanAssignmentsService.findPlanVersion(client, onDate);
    }

    private Bucket createBuckets(PlanVersion planVersion) throws IOException {
        return new ExpressionParser().parse(planVersion.getExpression());
    }

    private List<ReadingValue> findReadingValuesToInvoice(InvoiceRun invoiceRun, Client client) throws NoReadingValueFound {
        return readingValuesService.findReadingValues(invoiceRun.getSinceClosed(), invoiceRun.getUntilOpen(), client.getMeter());
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
        Invoice invoice = new Invoice(invoiceRun.format(invoiceNumber), invoiceRun.getIssueDate(), invoiceRun.getId(), client, invoiceGrossTotal);
        return invoicesRepository.save(invoice);
    }

    private void saveInvoiceLines(List<InvoiceLine> invoiceLines, Invoice savedInvoice) {
        for (InvoiceLine invoiceLine : invoiceLines) {
            invoiceLine.setInvoiceId(savedInvoice.getId());
        }
        invoiceLinesRepository.saveAll(invoiceLines);
    }

    private void printToPdf(InvoicePrintoutGenerationDescriptor descriptor, List<InvoiceLine> invoiceLines, Invoice invoice, Client client) throws ErrorGeneratingPdfPrintoutOfAnInvoice {
        invoicePrintoutGenerator.printToPdf(invoice, invoiceLines, client, descriptor);
    }
}
