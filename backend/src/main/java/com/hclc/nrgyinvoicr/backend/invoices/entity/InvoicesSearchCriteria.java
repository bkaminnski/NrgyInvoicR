package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.hclc.nrgyinvoicr.backend.PageDefinition;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_EXTENDED_UTC;

public class InvoicesSearchCriteria {

    @DateTimeFormat(pattern = ISO_8601_EXTENDED_UTC)
    private Date issueDateFrom;

    @DateTimeFormat(pattern = ISO_8601_EXTENDED_UTC)
    private Date issueDateTo;

    private PageDefinition pageDefinition;

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

    public PageDefinition getPageDefinition() {
        return pageDefinition;
    }

    public void setPageDefinition(PageDefinition pageDefinition) {
        this.pageDefinition = pageDefinition;
    }
}
