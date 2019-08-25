package com.hclc.nrgyinvoicr.systemtesting.stories.plans;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.UUID;

import static com.hclc.nrgyinvoicr.systemtesting.stories.plans.PlanVersionBuilder.aPlanVersion;
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
        app.findElement(By.id("ae-button-invoicing")).click();
        app.clickWith1sTimeout(By.id("ae-button-plans"));
    }

    private void openPlanVersionsOfAPlan(String planName) {
        WebElement weekendPlanRow = app.findElement(By.xpath("//*[@id='ae-table-plans']/mat-row/mat-cell[text()='" + planName + "']/.."));
        app.createActions().moveToElement(weekendPlanRow).perform();
        app.clickWith1sTimeout(By.id("ae-button-plan-versions"));
    }

    private void openPlanVersionDefinitionForm() {
        app.findElement(By.id("ae-button-define-plan-version")).click();
    }

    private PlanVersion defineANewPlanVersion(String planName) {
        String price = "0.18103";
        PlanVersion planVersion = aPlanVersion()
                .withValidSince("6/1/2019")
                .withValidSinceShortYear("6/1/19")
                .withSubscriptionFee("2.91")
                .withNetworkFee("4.13")
                .withDescription("A new version of a " + planName + ": " + UUID.randomUUID().toString())
                .withExpression("" +
                        ".01.01-12.31\n" +
                        "..1-5\n" +
                        "...0-23:0.19017\n" +
                        "..6-7\n" +
                        "...0-23:" + price
                )
                .build();
        app.findElement(By.id("ae-input-plan-version-valid-since")).sendKeys(planVersion.validSince);
        app.findElement(By.id("ae-input-plan-version-subscription-fee")).sendKeys(planVersion.subscriptionFee);
        app.findElement(By.id("ae-input-plan-version-network-fee")).sendKeys(planVersion.networkFee);
        app.findElement(By.id("ae-textarea-plan-version-description")).sendKeys(planVersion.description);
        app.findElement(By.id("ae-textarea-plan-version-expression")).clear();
        app.findElement(By.id("ae-textarea-plan-version-expression")).sendKeys(planVersion.expression);
        app.waitUpTo1sUntilElementIsVisible(By.xpath("//*[@id=\"ae-table-flattened-buckets\"]/mat-row/mat-cell[text()='" + price + "']"));
        app.findElement(By.id("ae-button-plan-version-save")).click();
        return planVersion;
    }

    public void assertThatUserSeesADefinedPlanVersionInAListOfPlanVersions(PlanVersion planVersion) {
        assertThatCode(() -> {
            WebElement planVersionRow = app.findElement(By.xpath("//*[@id='ae-table-plan-versions']/mat-row/mat-cell[text()='" + planVersion.description + "']/.."));
            planVersionRow.findElement(By.xpath("mat-cell[text()='" + planVersion.validSinceShortYear + "']"));
            planVersionRow.findElement(By.xpath("mat-cell[text()='" + planVersion.subscriptionFee + "']"));
            planVersionRow.findElement(By.xpath("mat-cell[text()='" + planVersion.networkFee + "']"));
        }).doesNotThrowAnyException();
    }

    public void assertThatPlanVersionDefinitionFormContainsAllFieldsForA(PlanVersion planVersion) {
        WebElement weekendPlanRow = app.findElement(By.xpath("//*[@id='ae-table-plan-versions']/mat-row/mat-cell[text()='" + planVersion.description + "']/.."));
        app.createActions().moveToElement(weekendPlanRow).perform();
        app.clickWith1sTimeout(By.id("ae-button-edit-plan-version"));
        assertThat(app.getValueOfElement(By.id("ae-input-plan-version-valid-since"))).isEqualTo(planVersion.validSince);
        assertThat(app.getValueOfElement(By.id("ae-input-plan-version-subscription-fee"))).isEqualTo(planVersion.subscriptionFee);
        assertThat(app.getValueOfElement(By.id("ae-input-plan-version-network-fee"))).isEqualTo(planVersion.networkFee);
        assertThat(app.getValueOfElement(By.id("ae-textarea-plan-version-description"))).isEqualTo(planVersion.description);
        assertThat(app.getValueOfElement(By.id("ae-textarea-plan-version-expression"))).isEqualTo(planVersion.expression);
        app.findElement(By.id("ae-button-plan-version-cancel")).click();
    }
}
