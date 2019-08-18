package com.hclc.nrgyinvoicr.backend.invoices.entity;

import java.util.List;

public class InvoicePrintout {
    private final List<InvoicePrintoutLine> invoicePrintoutLines;

    public InvoicePrintout(List<InvoicePrintoutLine> invoicePrintoutLines) {
        this.invoicePrintoutLines = invoicePrintoutLines;
    }

    public List<InvoicePrintoutLine> getInvoicePrintoutLines() {
        return invoicePrintoutLines;
    }
}
