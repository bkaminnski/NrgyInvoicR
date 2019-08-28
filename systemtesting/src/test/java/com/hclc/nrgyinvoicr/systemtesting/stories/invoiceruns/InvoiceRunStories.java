package com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;

class InvoiceRunStories {
    final Application app;

    InvoiceRunStories(Application app) {
        this.app = app;
    }

    void navigateToInvoiceRunsPage() {
        app.findElement(By.id("ae-button-invoicing")).click();
        app.clickWith1sTimeout(By.id("ae-button-invoice-runs"));
    }
}
