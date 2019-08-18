package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.clients.control.ClientsService;
import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoicePrintoutGenerationDescriptor;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunStatus.NEW;

@Component
public class InvoicesGeneratorStarter {
    private final InvoicePrintoutGenerator invoicePrintoutGenerator;
    private final ClientsService clientsService;
    private final ProgressTracker progressTracker;
    private final InvoicesGenerator invoicesGenerator;

    public InvoicesGeneratorStarter(InvoicesGenerator invoicesGenerator, InvoicePrintoutGenerator invoicePrintoutGenerator, ClientsService clientsService, ProgressTracker progressTracker) {
        this.invoicePrintoutGenerator = invoicePrintoutGenerator;
        this.clientsService = clientsService;
        this.progressTracker = progressTracker;
        this.invoicesGenerator = invoicesGenerator;
    }

    public InvoiceRun start(InvoiceRun invoiceRun) throws ErrorCompilingInvoicePrintoutTemplate {
        if (invoiceRun.getStatus() != NEW) {
            throw new IllegalStateException("Only new invoice run can be started.");
        }
        InvoicePrintoutGenerationDescriptor descriptor = invoicePrintoutGenerator.prepareDescriptor(invoiceRun);
        List<Client> clients = clientsService.findAll();
        InvoiceRun startedInvoiceRun = progressTracker.markAsStarted(invoiceRun, clients.size());
        invoicesGenerator.generateInvoices(descriptor, invoiceRun, clients);
        return startedInvoiceRun;
    }
}
