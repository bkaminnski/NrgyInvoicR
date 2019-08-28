package com.hclc.nrgyinvoicr.systemtesting.tests;

import com.hclc.nrgyinvoicr.systemtesting.SystemTest;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.Client;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientPlanAssignment;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientPlanAssignmentStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientRegistrationStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.MeterRegistrationStory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientsTest extends SystemTest {
    private Meter meter;

    @BeforeEach
    void beforeEach() {
        this.meter = new MeterRegistrationStory(app).userRegistersANewMeter();
    }

    @Test
    void userRegistersANewClient() {
        ClientRegistrationStory clientRegistrationStory = new ClientRegistrationStory(app);

        Client client = clientRegistrationStory.userRegistersANewClient(meter);

        clientRegistrationStory.assertThatUserSeesARegisteredClientInAListOfClients(client);
        clientRegistrationStory.assertThatClientRegistrationFormShowsAllValuesForA(client);
    }

    @Test
    void userAssignsAPlanToTheClient() {
        Client client = new ClientRegistrationStory(app).userRegistersANewClient(meter);
        ClientPlanAssignmentStory clientPlanAssignmentStory = new ClientPlanAssignmentStory(app);

        ClientPlanAssignment clientPlanAssignment = clientPlanAssignmentStory.userAssignsAPlanToTheClient(client, "Weekend Plan");

        clientPlanAssignmentStory.assertThatUserSeesAssignedPlanInTheListOfClientPlans(clientPlanAssignment);
        clientPlanAssignmentStory.assertThatClientPlanAssignmentFormShowsAllValuesForA(clientPlanAssignment);
    }
}
