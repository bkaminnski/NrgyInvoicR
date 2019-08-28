package com.hclc.nrgyinvoicr.systemtesting.tests;

import com.hclc.nrgyinvoicr.systemtesting.SystemTest;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.Client;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientPlanAssignment;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientPlanAssignmentStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientRegistrationStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRun;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRunCreationStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRunStartStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoices.InvoiceGenerationStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.MeterRegistrationStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.plans.PlanVersion;
import com.hclc.nrgyinvoicr.systemtesting.stories.plans.PlanVersionDefinitionStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.readings.ReadingUpload;
import com.hclc.nrgyinvoicr.systemtesting.stories.readings.ReadingUploadStory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class InvoiceRunsTest extends SystemTest {

    @Test
    void userCreatesAnInvoiceRun() {
        InvoiceRunCreationStory invoiceRunCreationStory = new InvoiceRunCreationStory(app);

        InvoiceRun invoiceRun = invoiceRunCreationStory.userCreatesANewInvoiceRun();

        invoiceRunCreationStory.assertThatUserSeesACreatedInvoiceRunInAListOfInvoiceRuns(invoiceRun);
    }

    @Test
    void userStartsAnInvoiceRun() {
        InvoiceRun invoiceRun = new InvoiceRunCreationStory(app).userCreatesANewInvoiceRun();
        InvoiceRunStartStory invoiceRunStartStory = new InvoiceRunStartStory(app);

        invoiceRunStartStory.userStartsAnInvoiceRun(invoiceRun);

        invoiceRunStartStory.assertThatUserSeesAFinishedInvoiceRunInAListOfInvoiceRuns(invoiceRun);
    }

    @Test
    void userGeneratesInvoices() throws IOException {
        PlanVersion planVersion = new PlanVersionDefinitionStory(app).userDefinesANewVersionOfAPlan("Weekend Plan");
        Meter meter = new MeterRegistrationStory(app).userRegistersANewMeter();
        ReadingUpload readingUpload = new ReadingUploadStory(app).userUploadsAReadingFile(meter);
        Client client = new ClientRegistrationStory(app).userRegistersANewClient(meter);
        ClientPlanAssignment clientPlanAssignment = new ClientPlanAssignmentStory(app).userAssignsAPlanToTheClient(client, "Weekend Plan");
        InvoiceRun invoiceRun = new InvoiceRunCreationStory(app).userCreatesANewInvoiceRun();
        InvoiceGenerationStory invoiceGenerationStory = new InvoiceGenerationStory(app);

        new InvoiceRunStartStory(app).userStartsAnInvoiceRun(invoiceRun);

        invoiceGenerationStory.assertThatInvoiceWasGeneratedForA(meter, client, invoiceRun, clientPlanAssignment, planVersion, readingUpload);
    }
}
