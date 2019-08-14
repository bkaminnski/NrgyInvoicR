package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.clients.control.ClientsService;
import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunStatus.NEW;

@Component
public class InvoicesGenerationStarter {
    private final ClientsService clientsService;
    private final ProgressTracker progressTracker;
    private final InvoicesGenerator invoicesGenerator;

    public InvoicesGenerationStarter(ClientsService clientsService, ProgressTracker progressTracker, InvoicesGenerator invoicesGenerator) {
        this.clientsService = clientsService;
        this.progressTracker = progressTracker;
        this.invoicesGenerator = invoicesGenerator;
    }

    public InvoiceRun start(InvoiceRun invoiceRun) {
        if (invoiceRun.getStatus() != NEW) {
            throw new IllegalStateException("Only new invoice run can be started.");
        }
        List<Client> clients = clientsService.findAll();
        InvoiceRun startedInvoiceRun = progressTracker.markAsStarted(invoiceRun, clients.size());
        invoicesGenerator.generateInvoices(invoiceRun, clients);
        return startedInvoiceRun;
    }
}
