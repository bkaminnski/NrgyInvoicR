package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;

public class InvoicePrintoutLine {
    private final Invoice invoice;
    private final InvoiceLine invoiceLine;
    private final Client client;

    public InvoicePrintoutLine(InvoiceLine invoiceLine) {
        this(null, invoiceLine, null);
    }

    public InvoicePrintoutLine(Invoice invoice, InvoiceLine invoiceLine, Client client) {
        this.invoice = invoice;
        this.invoiceLine = invoiceLine;
        this.client = client;
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
}
