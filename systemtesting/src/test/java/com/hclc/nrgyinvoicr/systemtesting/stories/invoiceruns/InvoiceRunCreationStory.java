package com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.UUID;

import static com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRunBuilder.anInvoiceRun;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class InvoiceRunCreationStory {
    private final Application app;

    public InvoiceRunCreationStory(Application app) {
        this.app = app;
    }

    public InvoiceRun userCreatesANewInvoiceRun() {
        navigateToInvoiceRunsPage();
        openInvoiceRunCreationForm();
        return createANewInvoiceRun();
    }

    private void navigateToInvoiceRunsPage() {
        app.findElement(By.id("ae-button-invoicing")).click();
        app.clickWith1sTimeout(By.id("ae-button-invoice-runs"));
    }

    private void openInvoiceRunCreationForm() {
        app.findElement(By.id("ae-button-create-invoice-run")).click();
    }

    private InvoiceRun createANewInvoiceRun() {
        InvoiceRun invoiceRun = anInvoiceRun()
                .withSince("7/1/2019")
                .withSinceShortYear("7/1/19")
                .withUntil("8/1/2019")
                .withUntilShortYear("8/1/19")
                .withIssueDate("8/12/2019")
                .withIssueDateShortYear("8/12/19")
                .withFirstInvoiceNumber("7")
                .withInvoiceNumberTemplate(UUID.randomUUID().toString() + "/%06d")
                .build();
        app.clearElementAndSendKeys(By.id("ae-input-invoice-run-since"), invoiceRun.since);
        app.clearElementAndSendKeys(By.id("ae-input-invoice-run-until"), invoiceRun.until);
        app.clearElementAndSendKeys(By.id("ae-input-invoice-run-issue-date"), invoiceRun.issueDate);
        app.clearElementAndSendKeys(By.id("ae-input-invoice-run-first-invoice-number"), invoiceRun.firstInvoiceNumber);
        app.clearElementAndSendKeys(By.id("ae-input-invoice-run-invoice-number-template"), invoiceRun.invoiceNumberTemplate);
        app.findElement(By.id("ae-button-invoice-run-save")).click();
        return invoiceRun;
    }

    public void assertThatUserSeesACreatedInvoiceRunInAListOfInvoiceRuns(InvoiceRun invoiceRun) {
        assertThatCode(() -> {
            WebElement clientRow = app.findElement(By.xpath("//*[@id='ae-table-invoice-runs']/tbody/tr/td[@id='ae-cell-invoice-run-number-template' and text()=' " + invoiceRun.invoiceNumberTemplate + " ']/.."));
            clientRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-since-closed' and text()=' " + invoiceRun.sinceShortYear + " ']"));
            clientRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-until-open' and text()=' " + invoiceRun.untilShortYear + " ']"));
            clientRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-issue-date' and text()=' " + invoiceRun.issueDateShortYear + " ']"));
            clientRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-first-invoice-number' and text()=' " + invoiceRun.firstInvoiceNumber + " ']"));
        }).doesNotThrowAnyException();
    }
}
