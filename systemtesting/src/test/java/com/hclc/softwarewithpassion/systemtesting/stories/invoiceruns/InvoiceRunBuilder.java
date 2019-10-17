package com.hclc.softwarewithpassion.systemtesting.stories.invoiceruns;

public class InvoiceRunBuilder {
    private String since;
    private String sinceShortYear;
    private String until;
    private String untilShortYear;
    private String issueDate;
    private String issueDateShortYear;
    private String firstInvoiceNumber;
    private String invoiceNumberTemplate;

    public static InvoiceRunBuilder anInvoiceRun() {
        return new InvoiceRunBuilder();
    }

    public InvoiceRunBuilder withSince(String since) {
        this.since = since;
        return this;
    }

    public InvoiceRunBuilder withSinceShortYear(String sinceShortYear) {
        this.sinceShortYear = sinceShortYear;
        return this;
    }

    public InvoiceRunBuilder withUntil(String until) {
        this.until = until;
        return this;
    }

    public InvoiceRunBuilder withUntilShortYear(String untilShortYear) {
        this.untilShortYear = untilShortYear;
        return this;
    }

    public InvoiceRunBuilder withIssueDate(String issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public InvoiceRunBuilder withIssueDateShortYear(String issueDateShortYear) {
        this.issueDateShortYear = issueDateShortYear;
        return this;
    }

    public InvoiceRunBuilder withFirstInvoiceNumber(String firstInvoiceNumber) {
        this.firstInvoiceNumber = firstInvoiceNumber;
        return this;
    }

    public InvoiceRunBuilder withInvoiceNumberTemplate(String invoiceNumberTemplate) {
        this.invoiceNumberTemplate = invoiceNumberTemplate;
        return this;
    }

    public InvoiceRun build() {
        return new InvoiceRun(since, sinceShortYear, until, untilShortYear, issueDate, issueDateShortYear, firstInvoiceNumber, invoiceNumberTemplate);
    }
}