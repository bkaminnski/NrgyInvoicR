package com.hclc.softwarewithpassion.systemtesting.stories.invoiceruns;

import com.hclc.softwarewithpassion.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceRunStartStory extends InvoiceRunStories {

    public InvoiceRunStartStory(Application app) {
        super(app);
    }

    public void userStartsAnInvoiceRun(InvoiceRun invoiceRun) {
        navigateToInvoiceRunsPage();
        startInvoiceRun(invoiceRun);
        waitUntilInvoiceRunIsFinished(invoiceRun);
    }

    private void startInvoiceRun(InvoiceRun invoiceRun) {
        app.hoverOverElement(By.xpath("//*[@id='ae-table-invoice-runs']/tbody/tr/td[text()=' " + invoiceRun.invoiceNumberTemplate + " ']/.."));
        app.clickWith1sTimeout(By.id("ae-button-invoice-run-start"));
    }

    private void waitUntilInvoiceRunIsFinished(InvoiceRun invoiceRun) {
        app.waitUpTo30sUntilElementIsVisible(By.xpath("//*[@id='ae-table-invoice-runs']/tbody/tr/td[text()=' " + invoiceRun.invoiceNumberTemplate + " ']/../td/mat-icon[text()=' done ' or text()=' error_outline ']"));
    }

    public void assertThatUserSeesAFinishedInvoiceRunInAListOfInvoiceRuns(InvoiceRun invoiceRun) {
        WebElement invoiceRunRow = findInvoiceRunRow(invoiceRun);
        int numberOfInvoices = parseInt(invoiceRunRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-number-of-invoices']")).getText().trim());
        int numberOfSuccesses = parseInt(invoiceRunRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-number-of-successes']")).getText().trim());
        int numberOfFailures = parseInt(invoiceRunRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-number-of-failures']")).getText().trim());
        assertThat(numberOfSuccesses + numberOfFailures).isEqualTo(numberOfInvoices);
    }
}
