package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;

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
        app.clickWith1sTimeout(By.id("ae-button-registries"));
        app.clickWith1sTimeout(By.id("ae-button-meters"));
    }

    private void openMeterRegistrationForm() {
        app.findElement(By.id("ae-button-register-meter")).click();
    }

    private Meter registerANewMeter() {
        Meter meter = MeterBuilder.aMeter().build();
        app.findElement(By.id("ae-input-meter-serial-number")).sendKeys(meter.serialNumber);
        app.findElement(By.id("ae-button-meter-save")).click();
        return meter;
    }

    public void assertThatUserSeesARegisteredMeterInAListOfMeters(Meter meter) {
        assertThatCode(() -> {
            app.findElement(By.xpath("//*[@id='ae-table-meters']/mat-row/mat-cell[@id='ae-cell-meter-serial-number' and text()=' " + meter.serialNumber + " ']/.."));
        }).doesNotThrowAnyException();
    }

    public void assertThatMeterRegistrationFormShowsAllValuesForA(Meter meter) {
        app.hoverOverElement(By.xpath("//*[@id='ae-table-meters']/mat-row/mat-cell[@id='ae-cell-meter-serial-number' and text()=' " + meter.serialNumber + " ']/.."));
        app.clickWith1sTimeout(By.id("ae-button-edit-meter"));
        assertThat(app.getValueOfElement(By.id("ae-input-meter-serial-number"))).isEqualTo(meter.serialNumber);
        app.findElement(By.id("ae-button-meter-cancel")).click();
    }
}
