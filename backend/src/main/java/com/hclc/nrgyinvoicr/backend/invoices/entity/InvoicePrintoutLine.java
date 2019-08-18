package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersion;

public class InvoicePrintoutLine {
    private final InvoiceLine invoiceLine;
    private final Invoice invoice;
    private final Client client;
    private final PlanVersion planVersion;
    private final InvoiceRun invoiceRun;

    public InvoicePrintoutLine(InvoiceLine invoiceLine) {
        this(invoiceLine, null);
    }

    public InvoicePrintoutLine(InvoiceLine invoiceLine, Invoice invoice) {
        this.invoiceLine = invoiceLine;
        if (invoice == null) {
            this.invoice = null;
            this.client = null;
            this.planVersion = null;
            this.invoiceRun = null;
        } else {
            this.invoice = invoice;
            this.client = invoice.getClient();
            this.planVersion = invoice.getPlanVersion();
            this.invoiceRun = invoice.getInvoiceRun();
        }
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
