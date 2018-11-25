package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class InvoicesSearchCriteria {

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date issueDateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date issueDateTo;

    public Date getIssueDateFrom() {
        return issueDateFrom;
    }

    public void setIssueDateFrom(Date issueDateFrom) {
        this.issueDateFrom = issueDateFrom;
    }

    public Date getIssueDateTo() {
        return issueDateTo;
    }

    public void setIssueDateTo(Date issueDateTo) {
        this.issueDateTo = issueDateTo;
    }
}
