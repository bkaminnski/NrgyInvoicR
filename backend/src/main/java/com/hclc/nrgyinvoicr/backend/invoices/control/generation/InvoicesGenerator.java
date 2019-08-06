package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
class InvoicesGenerator {
    private final ProgressTracker progressTracker;
    private final InvoiceGenerator invoiceGenerator;

    InvoicesGenerator(ProgressTracker progressTracker, InvoiceGenerator invoiceGenerator) {
        this.progressTracker = progressTracker;
        this.invoiceGenerator = invoiceGenerator;
    }

    @Async
    @Transactional(readOnly = true)
    public void generateInvoices(InvoiceRun invoiceRun, List<Client> clients) {
        try {
            tryGeneratingInvoices(invoiceRun, clients);
        } finally {
            this.progressTracker.markAsFinished(invoiceRun);
        }
    }

    private void tryGeneratingInvoices(InvoiceRun invoiceRun, List<Client> clients) {
        int invoiceNumber = invoiceRun.getFirstInvoiceNumber();
        for (Client client : clients) {
            invoiceGenerator.generateInvoice(invoiceRun, invoiceNumber, client);
            invoiceNumber++;
        }
    }
}
