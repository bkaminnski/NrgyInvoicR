package com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;

public class InvoiceRunStartStory extends InvoiceRunStories {

    public InvoiceRunStartStory(Application app) {
        super(app);
    }

    public void userStartsAnInvoiceRun(InvoiceRun invoiceRun) {
        navigateToInvoiceRunsPage();
        startInvoiceRun(invoiceRun);
    }

    private void startInvoiceRun(InvoiceRun invoiceRun) {
        app.hoverOverElement(By.xpath("//*[@id='ae-table-invoice-runs']/tbody/tr/td[text()=' " + invoiceRun.invoiceNumberTemplate + " ']/.."));
        app.clickWith1sTimeout(By.id("ae-button-invoice-run-start"));
    }

    public void assertThatUserSeesAFinishedInvoiceRunInAListOfInvoiceRuns(InvoiceRun invoiceRun) {
        app.waitUpTo30sUntilElementIsVisible(By.xpath("//*[@id='ae-table-invoice-runs']/tbody/tr/td[text()=' " + invoiceRun.invoiceNumberTemplate + " ']/../td/mat-icon[text()=' cloud_done ' or text()=' error_outline ']"));
    }
}
