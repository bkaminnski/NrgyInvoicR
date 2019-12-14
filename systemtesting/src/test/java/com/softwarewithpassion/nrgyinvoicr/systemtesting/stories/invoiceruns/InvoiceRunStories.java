package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoiceruns;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

class InvoiceRunStories {
    final Application app;

    InvoiceRunStories(Application app) {
        this.app = app;
    }

    void navigateToInvoiceRunsPage() {
        app.clickWith30sTimeout(By.id("ae-button-invoicing"));
        app.clickWith30sTimeout(By.id("ae-button-invoice-runs"));
    }

    WebElement findInvoiceRunRow(InvoiceRun invoiceRun) {
        return app.findElement(By.xpath("//*[@id='ae-table-invoice-runs']/tbody/tr/td[@id='ae-cell-invoice-run-number-template' and text()=' " + invoiceRun.invoiceNumberTemplate + " ']/.."));
    }
}
