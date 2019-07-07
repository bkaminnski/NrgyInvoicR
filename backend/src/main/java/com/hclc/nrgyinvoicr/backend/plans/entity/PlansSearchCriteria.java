package com.hclc.nrgyinvoicr.backend.plans.entity;

import com.hclc.nrgyinvoicr.backend.PageDefinition;

public class PlansSearchCriteria {
    private String name;
    private PageDefinition pageDefinition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageDefinition getPageDefinition() {
        return pageDefinition;
    }

    public void setPageDefinition(PageDefinition pageDefinition) {
        this.pageDefinition = pageDefinition;
    }
}
