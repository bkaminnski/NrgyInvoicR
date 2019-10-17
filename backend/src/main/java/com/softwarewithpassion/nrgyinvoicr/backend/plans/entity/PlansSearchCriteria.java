package com.softwarewithpassion.nrgyinvoicr.backend.plans.entity;

import com.softwarewithpassion.nrgyinvoicr.backend.PageDefinition;

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
