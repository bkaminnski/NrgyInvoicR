package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoiceruns;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class InvoiceRunCreationStory extends InvoiceRunStories {

    public InvoiceRunCreationStory(Application app) {
        super(app);
    }

    public InvoiceRun userCreatesANewInvoiceRun() {
        navigateToInvoiceRunsPage();
        openInvoiceRunCreationForm();
        return createANewInvoiceRun();
    }

    private void openInvoiceRunCreationForm() {
        app.findElement(By.id("ae-button-create-invoice-run")).click();
    }

    private InvoiceRun createANewInvoiceRun() {
        InvoiceRun invoiceRun = InvoiceRunBuilder.anInvoiceRun()
                .withSince("6/29/2019")
                .withSinceShortYear("6/29/19")
                .withUntil("8/3/2019")
                .withUntilShortYear("8/3/19")
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
            WebElement invoiceRunRow = findInvoiceRunRow(invoiceRun);
            invoiceRunRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-since-closed' and text()=' " + invoiceRun.sinceShortYear + " ']"));
            invoiceRunRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-until-open' and text()=' " + invoiceRun.untilShortYear + " ']"));
            invoiceRunRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-issue-date' and text()=' " + invoiceRun.issueDateShortYear + " ']"));
            invoiceRunRow.findElement(By.xpath("td[@id='ae-cell-invoice-run-first-invoice-number' and text()=' " + invoiceRun.firstInvoiceNumber + " ']"));
        }).doesNotThrowAnyException();
    }
}
