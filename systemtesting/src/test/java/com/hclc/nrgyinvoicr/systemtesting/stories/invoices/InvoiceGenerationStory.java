package com.hclc.nrgyinvoicr.systemtesting.stories.invoices;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.Client;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientPlanAssignment;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRun;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.hclc.nrgyinvoicr.systemtesting.stories.plans.PlanVersion;
import com.hclc.nrgyinvoicr.systemtesting.stories.readings.ReadingUpload;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import static com.hclc.nrgyinvoicr.systemtesting.stories.invoices.InvoiceLineBuilder.anInvoiceLine;
import static java.util.Comparator.naturalOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class InvoiceGenerationStory {
    private static final String INVOICES_OUTPUT_FOLDER = "/tmp";
    private Application app;

    public InvoiceGenerationStory(Application app) {
        this.app = app;
    }

    public void assertThatInvoiceWasGeneratedFor(InvoiceGenerationContext context) {
        navigateToInvoicesPage();
        searchInvoicesForA(context.client, context.invoiceRun);
        assertThatUserSeesGeneratedInvoice(context.meter, context.client, context.invoiceRun, context.clientPlanAssignment);
        assertThatUserSeesInvoiceLines(context.client, context.planVersion, context.readingUpload);
        assertThatInvoiceWasPrintedToPdf(context.client);
    }

    private void navigateToInvoicesPage() {
        app.findElement(By.id("ae-button-invoicing")).click();
        app.clickWith1sTimeout(By.id("ae-button-invoices"));
    }

    private void searchInvoicesForA(Client client, InvoiceRun invoiceRun) {
        app.clearElementAndSendKeys(By.id("ae-input-invoices-search-issue-date-since"), invoiceRun.since);
        app.clearElementAndSendKeys(By.id("ae-input-invoices-search-issue-date-until"), invoiceRun.since);
        app.clearElementAndSendKeys(By.id("ae-input-invoices-search-client-number"), client.number);
        app.findElement(By.id("ae-button-search-invoices")).click();
    }

    private void assertThatUserSeesGeneratedInvoice(Meter meter, Client client, InvoiceRun invoiceRun, ClientPlanAssignment clientPlanAssignment) {
        assertThatCode(() -> {
            WebElement invoiceRow = app.findElement(invoiceRow(client));
            invoiceRow.findElement(By.xpath("td[@id='ae-cell-invoice-number' and starts-with(text(), ' " + invoiceRun.getFirstPartOfInvoiceNumberTemplate() + "')]"));
            invoiceRow.findElement(By.xpath("td[@id='ae-cell-invoice-issue-date' and text()=' " + invoiceRun.issueDateShortYear + " ']"));
            invoiceRow.findElement(By.xpath("td[@id='ae-cell-invoice-gross-total' and text()=' 67.49 ']"));
            invoiceRow.findElement(By.xpath("td[@id='ae-cell-invoice-client-number' and text()=' " + client.number + " ']"));
            invoiceRow.findElement(By.xpath("td[@id='ae-cell-invoice-client-name' and text()=' " + client.fistName + " " + client.lastName + " ']"));
            invoiceRow.findElement(By.xpath("td[@id='ae-cell-invoice-meter-serial-number' and text()=' " + meter.serialNumber + " ']"));
            invoiceRow.findElement(By.xpath("td[@id='ae-cell-invoice-since-until' and text()=' " + invoiceRun.sinceShortYear + " - " + invoiceRun.untilShortYear + " ']"));
            invoiceRow.findElement(By.xpath("td[@id='ae-cell-invoice-plan-name' and text()=' " + clientPlanAssignment.planName + " ']"));
        }).doesNotThrowAnyException();
    }

    private By invoiceRow(Client client) {
        return By.xpath("//*[@id='ae-table-invoices']/tbody/tr/td[@id='ae-cell-invoice-client-number' and text()=' " + client.number + " ']/..");
    }

    private void assertThatUserSeesInvoiceLines(Client client, PlanVersion planVersion, ReadingUpload readingUpload) {
        app.hoverOverElement(invoiceRow(client));
        app.clickWith1sTimeout(By.id("ae-button-invoice-details"));
        assertThatUserSees(anInvoiceLine().withNumber("1").withDescription("Monday - Friday").withUnitPrice(planVersion.weekPrice).withQuantity(readingUpload.weekQuantity).withUnit("KWH").withNet("34.66").withVat("23%").withGross("42.63").build());
        assertThatUserSees(anInvoiceLine().withNumber("2").withDescription("Saturday - Sunday").withUnitPrice(planVersion.weekendPrice).withQuantity(readingUpload.weekendQuantity).withUnit("KWH").withNet("13.17").withVat("23%").withGross("16.2").build());
        assertThatUserSees(anInvoiceLine().withNumber("3").withDescription("Network fee").withUnitPrice("4.13").withQuantity("1").withUnit("NONE").withNet("4.13").withVat("23%").withGross("5.08").build());
        assertThatUserSees(anInvoiceLine().withNumber("4").withDescription("Subscription fee").withUnitPrice("2.91").withQuantity("1").withUnit("NONE").withNet("2.91").withVat("23%").withGross("3.58").build());
    }

    private void assertThatUserSees(InvoiceLine invoiceLine) {
        assertThatCode(() -> {
            WebElement invoiceLineRow = app.findElement(By.xpath("//*[@id='ae-table-invoice-lines']/mat-row/mat-cell[@id='ae-cell-invoice-line-number' and text()=' " + invoiceLine.number + " ']/.."));
            invoiceLineRow.findElement(By.xpath("mat-cell[@id='ae-cell-invoice-line-description' and text()=' " + invoiceLine.description + " ']"));
            invoiceLineRow.findElement(By.xpath("mat-cell[@id='ae-cell-invoice-line-unit-price' and text()=' " + invoiceLine.unitPrice + " ']"));
            invoiceLineRow.findElement(By.xpath("mat-cell[@id='ae-cell-invoice-line-quantity' and text()=' " + invoiceLine.quantity + " ']"));
            invoiceLineRow.findElement(By.xpath("mat-cell[@id='ae-cell-invoice-line-unit' and text()=' " + invoiceLine.unit + " ']"));
            invoiceLineRow.findElement(By.xpath("mat-cell[@id='ae-cell-invoice-line-net-total' and text()=' " + invoiceLine.net + " ']"));
            invoiceLineRow.findElement(By.xpath("mat-cell[@id='ae-cell-invoice-line-vat' and text()=' " + invoiceLine.vat + " ']"));
            invoiceLineRow.findElement(By.xpath("mat-cell[@id='ae-cell-invoice-line-gross-total' and text()=' " + invoiceLine.gross + " ']"));
        }).doesNotThrowAnyException();
    }

    private void assertThatInvoiceWasPrintedToPdf(Client client) {
        String localDate = LocalDate.now().toString();
        String lastOutputFolder = Stream.of(new File(INVOICES_OUTPUT_FOLDER).list())
                .filter(f -> f.startsWith(localDate))
                .filter(f -> new File(INVOICES_OUTPUT_FOLDER + "/" + f).isDirectory())
                .max(naturalOrder())
                .map(f -> INVOICES_OUTPUT_FOLDER + "/" + f)
                .orElse(null);
        Optional<String> generatedInvoicePdf = Stream.of(new File(lastOutputFolder).list())
                .filter(f -> f.endsWith(".pdf"))
                .filter(f -> f.startsWith("C_" + client.number))
                .findFirst()
                .map(f -> lastOutputFolder + "/" + f);
        assertThat(generatedInvoicePdf).isNotEmpty();
        assertThat(new File(generatedInvoicePdf.get()).length()).isGreaterThan(0);
    }
}
