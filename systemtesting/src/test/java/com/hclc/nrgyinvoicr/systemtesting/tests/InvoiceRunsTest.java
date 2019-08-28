package com.hclc.nrgyinvoicr.systemtesting.tests;

import com.hclc.nrgyinvoicr.systemtesting.SystemTest;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRun;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRunCreationStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRunStartStory;
import org.junit.jupiter.api.Test;

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
}
