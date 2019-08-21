package com.hclc.nrgyinvoicr.systemtesting.stories.clients;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientBuilder.aClient;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class ClientRegistrationStory {
    private final Application app;

    public ClientRegistrationStory(Application app) {
        this.app = app;
    }

    public Client userRegistersANewClient(Meter meter) {
        navigateToClientsPage();
        openClientRegistrationForm();
        return registerANewClient(meter);
    }

    private void navigateToClientsPage() {
        app.findElement(By.id("ae-button-registries")).click();
        app.clickWith1sTimeout(By.id("ae-button-clients"));
    }

    private void openClientRegistrationForm() {
        app.findElement(By.id("ae-button-register-client")).click();
    }

    private Client registerANewClient(Meter meter) {
        Client client = aClient().withMeterSerialNumber(meter.serialNumber).build();
        app.findElement(By.id("ae-input-client-first-name")).sendKeys(client.fistName);
        app.findElement(By.id("ae-input-client-middle-name")).sendKeys(client.middleName);
        app.findElement(By.id("ae-input-client-last-name")).sendKeys(client.lastName);
        app.findElement(By.id("ae-input-client-address-line-1")).sendKeys(client.addressLine1);
        app.findElement(By.id("ae-input-client-address-line-2")).sendKeys(client.addressLine2);
        app.findElement(By.id("ae-input-client-postal-code")).sendKeys(client.postalCode);
        app.findElement(By.id("ae-input-client-city")).sendKeys(client.city);
        app.findElement(By.id("ae-input-meter-autocomplete")).sendKeys(client.meterSerialNumber);
        app.clickWith1sTimeout(By.xpath("//mat-option/span[contains(text(), '" + client.meterSerialNumber + "')]/.."));
        app.findElement(By.id("ae-button-client-save")).click();
        return client;
    }

    public void assertThatUserSeesARegisteredClientInAListOfClients(Client client) {
        assertThatCode(() -> {
            WebElement clientRow = app.findElement(By.xpath("//*[@id='ae-table-clients']/mat-row/mat-cell[text()='" + client.lastName + "']/.."));
            clientRow.findElement(By.xpath("mat-cell[text()='" + client.fistName + "']"));
            clientRow.findElement(By.xpath("mat-cell[text()='" + client.addressLine1 + "']"));
            clientRow.findElement(By.xpath("mat-cell[text()='" + client.city + "']"));
            clientRow.findElement(By.xpath("mat-cell[text()='" + client.postalCode + "']"));
            clientRow.findElement(By.xpath("mat-cell[text()='" + client.meterSerialNumber + "']"));
        }).doesNotThrowAnyException();
    }
}
