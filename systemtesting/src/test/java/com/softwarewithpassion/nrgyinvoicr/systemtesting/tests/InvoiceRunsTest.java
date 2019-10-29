package com.softwarewithpassion.nrgyinvoicr.systemtesting.tests;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.SystemTest;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.clients.Client;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.clients.ClientPlanAssignment;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.clients.ClientPlanAssignmentStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.clients.ClientRegistrationStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRun;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRunCreationStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRunStartStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoices.InvoiceGenerationContext;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoices.InvoiceGenerationContextBuilder;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoices.InvoiceGenerationStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters.MeterRegistrationStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.plans.PlanVersion;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.plans.PlanVersionDefinitionStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.readings.ReadingUploadStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.readings.ReadingUpload;
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
        InvoiceGenerationContext invoiceGenerationContext = arrangeInvoiceGenerationContext();
        InvoiceGenerationStory invoiceGenerationStory = new InvoiceGenerationStory(app);

        new InvoiceRunStartStory(app).userStartsAnInvoiceRun(invoiceGenerationContext.invoiceRun);

        invoiceGenerationStory.assertThatInvoiceWasGeneratedFor(invoiceGenerationContext);
    }

    private InvoiceGenerationContext arrangeInvoiceGenerationContext() throws IOException {
        PlanVersion planVersion = new PlanVersionDefinitionStory(app).userDefinesANewVersionOfAPlan("Weekend Plan");
        Meter meter = new MeterRegistrationStory(app).userRegistersANewMeter();
        ReadingUpload readingUpload = new ReadingUploadStory(app).userUploadsAReadingFile(meter);
        Client client = new ClientRegistrationStory(app).userRegistersANewClient(meter);
        ClientPlanAssignment clientPlanAssignment = new ClientPlanAssignmentStory(app).userAssignsAPlanToTheClient(client, "Weekend Plan");
        InvoiceRun invoiceRun = new InvoiceRunCreationStory(app).userCreatesANewInvoiceRun();
        return InvoiceGenerationContextBuilder.anInvoiceGenerationContext()
                .withPlanVersion(planVersion)
                .withMeter(meter)
                .withReadingUpload(readingUpload)
                .withClient(client)
                .withClientPlanAssignment(clientPlanAssignment)
                .withInvoiceRun(invoiceRun)
                .build();
    }
}
