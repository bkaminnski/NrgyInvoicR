package com.hclc.softwarewithpassion.systemtesting.stories.invoiceruns;

public class InvoiceRun {
    public final String since;
    public final String sinceShortYear;
    public final String until;
    public final String untilShortYear;
    public final String issueDate;
    public final String issueDateShortYear;
    public final String firstInvoiceNumber;
    public final String invoiceNumberTemplate;

    InvoiceRun(String since, String sinceShortYear, String until, String untilShortYear, String issueDate, String issueDateShortYear, String firstInvoiceNumber, String invoiceNumberTemplate) {
        this.since = since;
        this.sinceShortYear = sinceShortYear;
        this.until = until;
        this.untilShortYear = untilShortYear;
        this.issueDate = issueDate;
        this.issueDateShortYear = issueDateShortYear;
        this.firstInvoiceNumber = firstInvoiceNumber;
        this.invoiceNumberTemplate = invoiceNumberTemplate;
    }

    public String getFirstPartOfInvoiceNumberTemplate() {
        return invoiceNumberTemplate.split("/")[0];
    }
}
