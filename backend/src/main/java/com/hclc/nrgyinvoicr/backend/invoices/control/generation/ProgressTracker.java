package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.invoices.control.InvoiceRunMessagesRepository;
import com.hclc.nrgyinvoicr.backend.invoices.control.InvoiceRunsRepository;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunMessage;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunProgress;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunStatus.FINISHED;
import static com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunStatus.STARTED;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Component
class ProgressTracker {
    private final InvoiceRunsRepository invoiceRunsRepository;
    private final InvoiceRunMessagesRepository invoiceRunMessagesRepository;

    ProgressTracker(InvoiceRunsRepository invoiceRunsRepository, InvoiceRunMessagesRepository invoiceRunMessagesRepository) {
        this.invoiceRunsRepository = invoiceRunsRepository;
        this.invoiceRunMessagesRepository = invoiceRunMessagesRepository;
    }

    @Transactional(propagation = REQUIRES_NEW)
    public InvoiceRun markAsStarted(InvoiceRun invoiceRun, int numberOfClients) {
        invoiceRun.setStatus(STARTED);
        invoiceRun.setProgress(new InvoiceRunProgress(numberOfClients));
        return invoiceRunsRepository.save(invoiceRun);
    }

    public InvoiceRun incrementSuccesses(InvoiceRun invoiceRun) {
        invoiceRun.getProgress().incrementSuccesses();
        return invoiceRun;
    }

    public InvoiceRun incrementFailures(InvoiceRun invoiceRun) {
        invoiceRun.getProgress().incrementFailure();
        return invoiceRun;
    }

    public void addMessage(InvoiceRun invoiceRun, String message) {
        invoiceRunMessagesRepository.save(new InvoiceRunMessage(invoiceRun, message));
    }

    @Transactional(propagation = REQUIRES_NEW)
    public InvoiceRun markAsFinished(InvoiceRun invoiceRun) {
        invoiceRun.setStatus(FINISHED);
        return invoiceRunsRepository.save(invoiceRun);
    }
}
