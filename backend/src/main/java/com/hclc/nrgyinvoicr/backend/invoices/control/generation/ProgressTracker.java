package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.invoices.control.InvoiceRunsRepository;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
class ProgressTracker {
    private final InvoiceRunsRepository invoiceRunsRepository;

    ProgressTracker(InvoiceRunsRepository invoiceRunsRepository) {
        this.invoiceRunsRepository = invoiceRunsRepository;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public InvoiceRun markAsStarted(InvoiceRun invoiceRun, int numberOfClients) {
        // TODO
        return invoiceRun;
    }

    public InvoiceRun incrementSuccesses(InvoiceRun invoiceRun) {
        // TODO
        return invoiceRun;
    }

    public InvoiceRun incrementFailures(InvoiceRun invoiceRun) {
        // TODO
        return invoiceRun;
    }

    public void addMessage(InvoiceRun invoiceRun, String message) {
        // TODO
    }

    @Transactional(propagation = REQUIRES_NEW)
    public InvoiceRun markAsFinished(InvoiceRun invoiceRun) {
        // TODO
        return invoiceRun;
    }
}
