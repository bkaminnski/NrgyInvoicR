package com.hclc.softwarewithpassion.systemtesting.stories.plans;

import com.hclc.softwarewithpassion.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class PlanVersionDefinitionStory {
    private final Application app;

    public PlanVersionDefinitionStory(Application app) {
        this.app = app;
    }

    public PlanVersion userDefinesANewVersionOfAPlan(String planName) {
        navigateToPlansPage();
        openPlanVersionsOfAPlan(planName);
        openPlanVersionDefinitionForm();
        return defineANewPlanVersion(planName);
    }

    private void navigateToPlansPage() {
        app.clickWith1sTimeout(By.id("ae-button-invoicing"));
        app.clickWith1sTimeout(By.id("ae-button-plans"));
    }

    private void openPlanVersionsOfAPlan(String planName) {
        app.hoverOverElement(By.xpath("//*[@id='ae-table-plans']/mat-row/mat-cell[text()='" + planName + "']/.."));
        app.clickWith1sTimeout(By.id("ae-button-plan-versions"));
    }

    private void openPlanVersionDefinitionForm() {
        app.findElement(By.id("ae-button-define-plan-version")).click();
    }

    private PlanVersion defineANewPlanVersion(String planName) {
        String weekPrice = "0.19017";
        String weekendPrice = "0.18103";
        PlanVersion planVersion = PlanVersionBuilder.aPlanVersion()
                .withValidSince("6/1/2019")
                .withValidSinceShortYear("6/1/19")
                .withSubscriptionFee("2.91")
                .withNetworkFee("4.13")
                .withDescription("A new version of a " + planName + ": " + UUID.randomUUID().toString())
                .withExpression("" +
                        ".01.01-12.31\n" +
                        "..1-5\n" +
                        "...0-23:" + weekPrice + "\n" +
                        "..6-7\n" +
                        "...0-23:" + weekendPrice
                )
                .withWeekPrice(weekPrice)
                .withWeekendPrice(weekendPrice)
                .build();
        app.findElement(By.id("ae-input-plan-version-valid-since")).sendKeys(planVersion.validSince);
        app.findElement(By.id("ae-input-plan-version-subscription-fee")).sendKeys(planVersion.subscriptionFee);
        app.findElement(By.id("ae-input-plan-version-network-fee")).sendKeys(planVersion.networkFee);
        app.findElement(By.id("ae-textarea-plan-version-description")).sendKeys(planVersion.description);
        app.clearElementAndSendKeys(By.id("ae-textarea-plan-version-expression"), planVersion.expression);
        app.waitUpTo1sUntilElementIsVisible(By.xpath("//*[@id=\"ae-table-flattened-buckets\"]/mat-row/mat-cell[text()='" + weekendPrice + "']"));
        app.findElement(By.id("ae-button-plan-version-save")).click();
        return planVersion;
    }

    public void assertThatUserSeesADefinedPlanVersionInAListOfPlanVersions(PlanVersion planVersion) {
        assertThatCode(() -> {
            WebElement planVersionRow = app.findElement(By.xpath("//*[@id='ae-table-plan-versions']/mat-row/mat-cell[@id='ae-cell-plan-version-description' and text()=' " + planVersion.description + " ']/.."));
            planVersionRow.findElement(By.xpath("mat-cell[@id='ae-cell-plan-version-valid-since' and text()=' " + planVersion.validSinceShortYear + " ']"));
            planVersionRow.findElement(By.xpath("mat-cell[@id='ae-cell-plan-version-subscription-fee' and text()=' " + planVersion.subscriptionFee + " ']"));
            planVersionRow.findElement(By.xpath("mat-cell[@id='ae-cell-plan-version-network-fee' and text()=' " + planVersion.networkFee + " ']"));
        }).doesNotThrowAnyException();
    }

    public void assertThatPlanVersionDefinitionFormShowsAllValuesForA(PlanVersion planVersion) {
        app.hoverOverElement(By.xpath("//*[@id='ae-table-plan-versions']/mat-row/mat-cell[@id='ae-cell-plan-version-description' and text()=' " + planVersion.description + " ']/.."));
        app.clickWith1sTimeout(By.id("ae-button-edit-plan-version"));
        assertThat(app.getValueOfElement(By.id("ae-input-plan-version-valid-since"))).isEqualTo(planVersion.validSince);
        assertThat(app.getValueOfElement(By.id("ae-input-plan-version-subscription-fee"))).isEqualTo(planVersion.subscriptionFee);
        assertThat(app.getValueOfElement(By.id("ae-input-plan-version-network-fee"))).isEqualTo(planVersion.networkFee);
        assertThat(app.getValueOfElement(By.id("ae-textarea-plan-version-description"))).isEqualTo(planVersion.description);
        assertThat(app.getValueOfElement(By.id("ae-textarea-plan-version-expression"))).isEqualTo(planVersion.expression);
        app.findElement(By.id("ae-button-plan-version-cancel")).click();
    }
}
