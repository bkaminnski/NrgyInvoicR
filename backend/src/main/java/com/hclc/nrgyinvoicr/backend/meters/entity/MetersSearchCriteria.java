package com.hclc.nrgyinvoicr.backend.meters.entity;

import com.hclc.nrgyinvoicr.backend.PageDefinition;

public class MetersSearchCriteria {
    private String serialNumber;
    private PageDefinition pageDefinition;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public PageDefinition getPageDefinition() {
        return pageDefinition;
    }

    public void setPageDefinition(PageDefinition pageDefinition) {
        this.pageDefinition = pageDefinition;
    }
}
