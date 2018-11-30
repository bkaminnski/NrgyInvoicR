package com.hclc.nrgyinvoicr.backend;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageDefinition {

    private String sortColumn;

    private String sortDirection;

    private int pageNumber;

    private int pageSize;

    public PageRequest asPageRequest() {
        return PageRequest.of(pageNumber, pageSize, Sort.Direction.fromString(sortDirection), sortColumn);
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
