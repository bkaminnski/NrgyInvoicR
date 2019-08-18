package com.hclc.nrgyinvoicr.backend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneId;

@Component
@ConfigurationProperties(prefix = "nrgyinvoicr")
public class NrgyInvoicRConfig {
    private String timeZone;
    private String invoiceNumberTemplate;
    private String vat;
    private String invoiceFolder;

    public ZoneId getTimeZoneAsZoneId() {
        return ZoneId.of(timeZone);
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getInvoiceNumberTemplate() {
        return invoiceNumberTemplate;
    }

    public void setInvoiceNumberTemplate(String invoiceNumberTemplate) {
        this.invoiceNumberTemplate = invoiceNumberTemplate;
    }

    public BigDecimal getVatAsBigDecimal() {
        return new BigDecimal(vat);
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getInvoiceFolder() {
        return invoiceFolder;
    }

    public void setInvoiceFolder(String invoiceFolder) {
        this.invoiceFolder = invoiceFolder;
    }
}
