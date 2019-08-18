package com.hclc.nrgyinvoicr.backend.invoices.entity;

import net.sf.jasperreports.engine.JasperReport;

public class InvoicePrintoutGenerationDescriptor {
    private final JasperReport compiledTemplate;
    private final String folderName;

    public InvoicePrintoutGenerationDescriptor(JasperReport compiledTemplate, String folderName) {
        this.compiledTemplate = compiledTemplate;
        this.folderName = folderName;
    }

    public JasperReport getCompiledTemplate() {
        return compiledTemplate;
    }

    public String getFolderName() {
        return folderName;
    }
}
