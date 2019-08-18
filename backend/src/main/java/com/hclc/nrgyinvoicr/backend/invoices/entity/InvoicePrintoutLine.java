package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersion;

public class InvoicePrintoutLine {
    private final Invoice invoice;
    private final InvoiceLine invoiceLine;
    private final Client client;
    private final PlanVersion planVersion;

    public InvoicePrintoutLine(InvoiceLine invoiceLine) {
        this(null, invoiceLine);
    }

    public InvoicePrintoutLine(Invoice invoice, InvoiceLine invoiceLine) {
        this.invoiceLine = invoiceLine;
        if (invoice == null) {
            this.invoice = null;
            this.client = null;
            this.planVersion = null;
        } else {
            this.invoice = invoice;
            this.client = invoice.getClient();
            this.planVersion = invoice.getPlanVersion();
        }
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public InvoiceLine getInvoiceLine() {
        return invoiceLine;
    }

    public Client getClient() {
        return client;
    }

    public PlanVersion getPlanVersion() {
        return planVersion;
    }
}
