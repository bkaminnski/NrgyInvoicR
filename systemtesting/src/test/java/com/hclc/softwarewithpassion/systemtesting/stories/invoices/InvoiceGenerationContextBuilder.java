package com.hclc.softwarewithpassion.systemtesting.stories.invoices;

import com.hclc.softwarewithpassion.systemtesting.stories.clients.Client;
import com.hclc.softwarewithpassion.systemtesting.stories.clients.ClientPlanAssignment;
import com.hclc.softwarewithpassion.systemtesting.stories.invoiceruns.InvoiceRun;
import com.hclc.softwarewithpassion.systemtesting.stories.meters.Meter;
import com.hclc.softwarewithpassion.systemtesting.stories.plans.PlanVersion;
import com.hclc.softwarewithpassion.systemtesting.stories.readings.ReadingUpload;

public class InvoiceGenerationContextBuilder {
    private PlanVersion planVersion;
    private Meter meter;
    private ReadingUpload readingUpload;
    private Client client;
    private ClientPlanAssignment clientPlanAssignment;
    private InvoiceRun invoiceRun;

    public static InvoiceGenerationContextBuilder anInvoiceGenerationContext() {
        return new InvoiceGenerationContextBuilder();
    }

    public InvoiceGenerationContextBuilder withPlanVersion(PlanVersion planVersion) {
        this.planVersion = planVersion;
        return this;
    }

    public InvoiceGenerationContextBuilder withMeter(Meter meter) {
        this.meter = meter;
        return this;
    }

    public InvoiceGenerationContextBuilder withReadingUpload(ReadingUpload readingUpload) {
        this.readingUpload = readingUpload;
        return this;
    }

    public InvoiceGenerationContextBuilder withClient(Client client) {
        this.client = client;
        return this;
    }

    public InvoiceGenerationContextBuilder withClientPlanAssignment(ClientPlanAssignment clientPlanAssignment) {
        this.clientPlanAssignment = clientPlanAssignment;
        return this;
    }

    public InvoiceGenerationContextBuilder withInvoiceRun(InvoiceRun invoiceRun) {
        this.invoiceRun = invoiceRun;
        return this;
    }

    public InvoiceGenerationContext build() {
        return new InvoiceGenerationContext(planVersion, meter, readingUpload, client, clientPlanAssignment, invoiceRun);
    }
}