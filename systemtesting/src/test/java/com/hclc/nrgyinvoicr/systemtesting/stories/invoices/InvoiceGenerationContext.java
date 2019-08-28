package com.hclc.nrgyinvoicr.systemtesting.stories.invoices;

import com.hclc.nrgyinvoicr.systemtesting.stories.clients.Client;
import com.hclc.nrgyinvoicr.systemtesting.stories.clients.ClientPlanAssignment;
import com.hclc.nrgyinvoicr.systemtesting.stories.invoiceruns.InvoiceRun;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.hclc.nrgyinvoicr.systemtesting.stories.plans.PlanVersion;
import com.hclc.nrgyinvoicr.systemtesting.stories.readings.ReadingUpload;

public class InvoiceGenerationContext {
    public final PlanVersion planVersion;
    public final Meter meter;
    public final ReadingUpload readingUpload;
    public final Client client;
    public final ClientPlanAssignment clientPlanAssignment;
    public final InvoiceRun invoiceRun;

    public InvoiceGenerationContext(PlanVersion planVersion, Meter meter, ReadingUpload readingUpload, Client client, ClientPlanAssignment clientPlanAssignment, InvoiceRun invoiceRun) {
        this.planVersion = planVersion;
        this.meter = meter;
        this.readingUpload = readingUpload;
        this.client = client;
        this.clientPlanAssignment = clientPlanAssignment;
        this.invoiceRun = invoiceRun;
    }
}
