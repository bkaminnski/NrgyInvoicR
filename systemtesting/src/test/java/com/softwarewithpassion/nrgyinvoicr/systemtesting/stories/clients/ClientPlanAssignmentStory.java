package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.clients;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class ClientPlanAssignmentStory extends ClientStories {

    public ClientPlanAssignmentStory(Application app) {
        super(app);
    }

    public ClientPlanAssignment userAssignsAPlanToTheClient(Client client, String planName) {
        navigateToClientsPage();
        navigateToPlansAssignmentPage(client);
        openPlanAssignmentForm();
        return assignAPlan(planName);
    }

    private void navigateToPlansAssignmentPage(Client client) {
        app.hoverOverElement(By.xpath("//*[@id='ae-table-clients']/mat-row/mat-cell[text()=' " + client.lastName + " ']/.."));
        app.clickWith1sTimeout(By.id("ae-button-plans"));
    }

    private void openPlanAssignmentForm() {
        app.findElement(By.id("ae-button-assign-plan")).click();
    }

    private ClientPlanAssignment assignAPlan(String planName) {
        ClientPlanAssignment clientPlanAssignment = ClientPlanAssignmentBuilder.aClientPlanAssignment()
                .withPlanName(planName)
                .withValidSince("3/1/2019")
                .withValidSinceShortYear("3/1/19")
                .build();
        app.findElement(By.id("ae-input-client-plan-valid-since")).sendKeys(clientPlanAssignment.validSince);
        app.findElement(By.id("ae-input-plan-autocomplete")).sendKeys(clientPlanAssignment.planName);
        app.clickWith1sTimeout(By.xpath("//mat-option/span[text()=' " + clientPlanAssignment.planName + " ']/.."));
        app.findElement(By.id("ae-button-client-plan-save")).click();
        return clientPlanAssignment;
    }

    public void assertThatUserSeesAssignedPlanInTheListOfClientPlans(ClientPlanAssignment clientPlanAssignment) {
        assertThatCode(() -> {
            WebElement clientRow = app.findElement(By.xpath("//*[@id='ae-table-client-plans']/mat-row/mat-cell[@id='ae-cell-client-plan-name' and text()=' " + clientPlanAssignment.planName + " ']/.."));
            clientRow.findElement(By.xpath("mat-cell[@id='ae-cell-client-plan-valid-since' and text()=' " + clientPlanAssignment.validSinceShortYear + " ']"));
        }).doesNotThrowAnyException();
    }

    public void assertThatClientPlanAssignmentFormShowsAllValuesForA(ClientPlanAssignment clientPlanAssignment) {
        app.hoverOverElement(By.xpath("//*[@id='ae-table-client-plans']/mat-row/mat-cell[text()=' " + clientPlanAssignment.planName + " ']/.."));
        app.clickWith1sTimeout(By.id("ae-button-edit-plan-assignment"));
        assertThat(app.getValueOfElement(By.id("ae-input-client-plan-valid-since"))).isEqualTo(clientPlanAssignment.validSince);
        assertThat(app.getValueOfElement(By.id("ae-input-plan-autocomplete"))).isEqualTo(clientPlanAssignment.planName);
        app.findElement(By.id("ae-button-client-plan-cancel")).click();
    }
}
