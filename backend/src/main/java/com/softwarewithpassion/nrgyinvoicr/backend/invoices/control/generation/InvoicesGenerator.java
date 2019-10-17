package com.softwarewithpassion.nrgyinvoicr.backend.invoices.control.generation;

import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.Client;
import com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity.InvoicePrintoutGenerationDescriptor;
import com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
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
    public void generateInvoices(InvoicePrintoutGenerationDescriptor descriptor, InvoiceRun invoiceRun, List<Client> clients) {
        try {
            tryGeneratingInvoices(descriptor, invoiceRun, clients);
        } finally {
            this.progressTracker.markAsFinished(invoiceRun);
        }
    }

    private void tryGeneratingInvoices(InvoicePrintoutGenerationDescriptor descriptor, InvoiceRun invoiceRun, List<Client> clients) {
        int invoiceNumber = invoiceRun.getFirstInvoiceNumber();
        for (Client client : clients) {
            invoiceGenerator.generateInvoice(descriptor, invoiceRun, invoiceNumber, client);
            invoiceNumber++;
        }
    }
}
