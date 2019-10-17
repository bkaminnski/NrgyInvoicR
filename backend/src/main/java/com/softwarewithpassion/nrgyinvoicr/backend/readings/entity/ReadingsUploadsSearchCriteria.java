package com.softwarewithpassion.nrgyinvoicr.backend.readings.entity;

import com.softwarewithpassion.nrgyinvoicr.backend.PageDefinition;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public class ReadingsUploadsSearchCriteria {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateSince;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateUntil;

    private boolean includeErrors;

    private PageDefinition pageDefinition;

    public ZonedDateTime getDateSince() {
        return dateSince;
    }

    public void setDateSince(ZonedDateTime dateSince) {
        this.dateSince = dateSince;
    }

    public ZonedDateTime getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(ZonedDateTime dateUntil) {
        this.dateUntil = dateUntil;
    }

    public boolean isIncludeErrors() {
        return includeErrors;
    }

    public void setIncludeErrors(boolean includeErrors) {
        this.includeErrors = includeErrors;
    }

    public PageDefinition getPageDefinition() {
        return pageDefinition;
    }

    public void setPageDefinition(PageDefinition pageDefinition) {
        this.pageDefinition = pageDefinition;
    }
}
