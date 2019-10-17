package com.softwarewithpassion.nrgyinvoicr.backend.meters.entity;

import com.softwarewithpassion.nrgyinvoicr.backend.PageDefinition;

public class MetersSearchCriteria {
    private String serialNumber;
    private boolean onlyUnassigned;
    private PageDefinition pageDefinition;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isOnlyUnassigned() {
        return onlyUnassigned;
    }

    public void setOnlyUnassigned(boolean onlyUnassigned) {
        this.onlyUnassigned = onlyUnassigned;
    }

    public PageDefinition getPageDefinition() {
        return pageDefinition;
    }

    public void setPageDefinition(PageDefinition pageDefinition) {
        this.pageDefinition = pageDefinition;
    }
}
