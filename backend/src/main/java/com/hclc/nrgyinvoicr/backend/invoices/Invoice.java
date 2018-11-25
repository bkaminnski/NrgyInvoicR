package com.hclc.nrgyinvoicr.backend.invoices;

import java.util.Date;

public class Invoice {

    private Long id;
    private String number;
    private Date issueDate;

    public Invoice(Long id, String number, Date issueDate) {
        this.id = id;
        this.number = number;
        this.issueDate = issueDate;
    }

    public boolean matches(InvoicesSearchCriteria invoicesSearchCriteria) {
        Date from = invoicesSearchCriteria.getIssueDateFrom();
        Date to = invoicesSearchCriteria.getIssueDateTo();
        return (from == null || !issueDate.before(from)) &&
                (to == null || !issueDate.after(to));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}
