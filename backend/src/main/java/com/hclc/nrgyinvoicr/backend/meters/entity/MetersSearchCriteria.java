package com.hclc.nrgyinvoicr.backend.meters.entity;

import com.hclc.nrgyinvoicr.backend.PageDefinition;

public class MetersSearchCriteria {
    private String externalId;
    private PageDefinition pageDefinition;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public PageDefinition getPageDefinition() {
        return pageDefinition;
    }

    public void setPageDefinition(PageDefinition pageDefinition) {
        this.pageDefinition = pageDefinition;
    }
}
