package com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity;

import com.softwarewithpassion.nrgyinvoicr.backend.PageDefinition;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public class InvoicesSearchCriteria {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime issueDateSince;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime issueDateUntil;

    private String clientNumber;

    private PageDefinition pageDefinition;

    public ZonedDateTime getIssueDateSince() {
        return issueDateSince;
    }

    public void setIssueDateSince(ZonedDateTime issueDateSince) {
        this.issueDateSince = issueDateSince;
    }

    public ZonedDateTime getIssueDateUntil() {
        return issueDateUntil;
    }

    public void setIssueDateUntil(ZonedDateTime issueDateUntil) {
        this.issueDateUntil = issueDateUntil;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public PageDefinition getPageDefinition() {
        return pageDefinition;
    }

    public void setPageDefinition(PageDefinition pageDefinition) {
        this.pageDefinition = pageDefinition;
    }
}
