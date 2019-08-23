package com.hclc.nrgyinvoicr.systemtesting.stories.meters;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.hclc.nrgyinvoicr.systemtesting.stories.meters.MeterBuilder.aMeter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class MeterRegistrationStory {
    private final Application app;

    public MeterRegistrationStory(Application app) {
        this.app = app;
    }

    public Meter userRegistersANewMeter() {
        navigateToMetersPage();
        openMeterRegistrationForm();
        return registerANewMeter();
    }

    private void navigateToMetersPage() {
        app.findElement(By.id("ae-button-registries")).click();
        app.clickWith1sTimeout(By.id("ae-button-meters"));
    }

    private void openMeterRegistrationForm() {
        app.findElement(By.id("ae-button-register-meter")).click();
    }

    private Meter registerANewMeter() {
        Meter meter = aMeter().build();
        app.findElement(By.id("ae-input-meter-serial-number")).sendKeys(meter.serialNumber);
        app.findElement(By.id("ae-button-meter-save")).click();
        return meter;
    }

    public void assertThatUserSeesARegisteredMeterInAListOfMeters(Meter meter) {
        assertThatCode(() -> {
            app.findElement(By.xpath("//*[@id='ae-table-meters']/mat-row/mat-cell[text()='" + meter.serialNumber + "']/.."));
        }).doesNotThrowAnyException();
    }

    public void assertThatMeterRegistrationFormContainsAllFieldsForA(Meter meter) {
        WebElement meterRow = app.findElement(By.xpath("//*[@id='ae-table-meters']/mat-row/mat-cell[text()='" + meter.serialNumber + "']/.."));
        app.createActions().moveToElement(meterRow).perform();
        app.clickWith1sTimeout(By.id("ae-button-edit-meter"));
        assertThat(app.getValueOfElement(By.id("ae-input-meter-serial-number"))).isEqualTo(meter.serialNumber);
        app.findElement(By.id("ae-button-meter-cancel")).click();
    }
}
