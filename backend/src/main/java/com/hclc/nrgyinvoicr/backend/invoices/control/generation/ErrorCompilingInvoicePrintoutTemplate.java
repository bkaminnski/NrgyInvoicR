package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

public class ErrorCompilingInvoicePrintoutTemplate extends Exception {
    ErrorCompilingInvoicePrintoutTemplate(Exception e) {
        super("There was an error compiling invoice printout template. " + e.getMessage());
    }
}
