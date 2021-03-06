package com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity;

import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.Client;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlanVersion;

public class InvoicePrintoutLine {
    private final InvoiceLine invoiceLine;
    private final Invoice invoice;
    private final Client client;
    private final PlanVersion planVersion;
    private final InvoiceRun invoiceRun;

    public InvoicePrintoutLine(InvoiceLine invoiceLine, Invoice invoice) {
        this.invoiceLine = invoiceLine;
        this.invoice = invoice;
        this.client = invoice.getClient();
        this.planVersion = invoice.getPlanVersion();
        this.invoiceRun = invoice.getInvoiceRun();
    }

    public InvoiceLine getInvoiceLine() {
        return invoiceLine;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Client getClient() {
        return client;
    }

    public PlanVersion getPlanVersion() {
        return planVersion;
    }

    public InvoiceRun getInvoiceRun() {
        return invoiceRun;
    }
}
