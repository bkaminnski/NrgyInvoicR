package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hclc.nrgyinvoicr.backend.NrgyInvoicRConfig;
import com.hclc.nrgyinvoicr.backend.invoices.entity.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.DATE_TIME_FILE_NAME_SAFE;
import static java.time.ZoneId.systemDefault;

@Component
class InvoicePrintoutGenerator {
    private static final Logger logger = Logger.getLogger(InvoicePrintoutGenerator.class.getName());
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FILE_NAME_SAFE);
    private static final String TEMPLATE_NAME = "nrgy_invoicr_invoice.jrxml";
    private final NrgyInvoicRConfig nrgyInvoicRConfig;
    private final ObjectMapper objectMapper;
    private final Clock clock;

    public InvoicePrintoutGenerator(NrgyInvoicRConfig nrgyInvoicRConfig, ObjectMapper objectMapper, Clock clock) {
        this.nrgyInvoicRConfig = nrgyInvoicRConfig;
        this.objectMapper = objectMapper;
        this.clock = clock;
    }

    InvoicePrintoutGenerationDescriptor prepareDescriptor(InvoiceRun invoiceRun) throws ErrorCompilingInvoicePrintoutTemplate {
        JasperReport compiledTemplate;
        try {
            compiledTemplate = compileTemplate();
        } catch (JRException e) {
            logger.log(Level.SEVERE, "", e);
            throw new ErrorCompilingInvoicePrintoutTemplate(e);
        }
        String folderName = prepareFolderName(invoiceRun);
        return new InvoicePrintoutGenerationDescriptor(compiledTemplate, folderName);
    }

    private JasperReport compileTemplate() throws JRException {
        JasperReport compiledTemplate;
        String templateFilePathWithName = nrgyInvoicRConfig.getInvoiceFolder() + TEMPLATE_NAME;
        if (new File(templateFilePathWithName).canRead()) {
            compiledTemplate = JasperCompileManager.compileReport(templateFilePathWithName);
        } else {
            InputStream resourceAsStream = InvoicePrintoutGenerator.class.getResourceAsStream("/templates/" + TEMPLATE_NAME);
            compiledTemplate = JasperCompileManager.compileReport(resourceAsStream);
        }
        return compiledTemplate;
    }

    private String prepareFolderName(InvoiceRun invoiceRun) {
        return formatter.format(clock.instant().atZone(systemDefault())) + "_IR_" + invoiceRun.getId();
    }

    void printToPdf(Invoice invoice, List<InvoiceLine> invoiceLines, InvoicePrintoutGenerationDescriptor descriptor) throws ErrorGeneratingPdfPrintoutOfAnInvoice {
        try {
            String invoiceAsJsonString = serializeToJsonString(invoice, invoiceLines);
            JasperPrint jasperPrint = fillIn(descriptor.getCompiledTemplate(), invoiceAsJsonString);
            exportToPdf(jasperPrint, descriptor.getFolderName(), preparePdfFileName(invoice));
        } catch (JRException | IOException e) {
            logger.log(Level.SEVERE, "", e);
            throw new ErrorGeneratingPdfPrintoutOfAnInvoice(invoice.getClient(), e);
        }
    }

    private String preparePdfFileName(Invoice invoice) {
        return "C_" + invoice.getClient().getNumber() + "_" + formatter.format(clock.instant().atZone(systemDefault())) + "_I_" + invoice.getId() + ".pdf";
    }

    private String serializeToJsonString(Invoice invoice, List<InvoiceLine> invoiceLines) throws JsonProcessingException {
        List<InvoicePrintoutLine> invoicePrintoutLines = prepareInvoiceLinesKeepingInvoiceAndClientOnlyInFirstElement(invoice, invoiceLines);
        return this.objectMapper.writeValueAsString(invoicePrintoutLines);
    }

    private List<InvoicePrintoutLine> prepareInvoiceLinesKeepingInvoiceAndClientOnlyInFirstElement(Invoice invoice, List<InvoiceLine> invoiceLines) {
        List<InvoicePrintoutLine> invoicePrintoutLines = new ArrayList<>();
        boolean invoiceAndClientAdded = false;
        for (InvoiceLine invoiceLine : invoiceLines) {
            invoicePrintoutLines.add(invoiceAndClientAdded ? new InvoicePrintoutLine(invoiceLine) : new InvoicePrintoutLine(invoiceLine, invoice));
            invoiceAndClientAdded = true;
        }
        return invoicePrintoutLines;
    }

    private JasperPrint fillIn(JasperReport jasperReport, String invoiceAsJsonString) throws JRException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(invoiceAsJsonString.getBytes());
        JRDataSource dataSource = new JsonDataSource(inputStream);
        Map<String, Object> parameters = new HashMap<>();
        return JasperFillManager.fillReport(jasperReport, parameters, dataSource);
    }

    private void exportToPdf(JasperPrint jasperPrint, String folderName, String fileName) throws JRException, IOException {
        String path = nrgyInvoicRConfig.getInvoiceFolder() + folderName;
        new File(path).mkdirs();
        String pdfFilePathWithName = path + "/" + fileName;
        OutputStream output = new FileOutputStream(new File(pdfFilePathWithName));
        JasperExportManager.exportReportToPdfStream(jasperPrint, output);
        output.close();
    }
}
