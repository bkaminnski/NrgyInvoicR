package com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InvoiceRunProgress {

    @Column
    private Integer numberOfInvoicesToGenerate;

    @Column
    private Integer numberOfSuccesses;

    @Column
    private Integer numberOfFailures;

    protected InvoiceRunProgress() {
    }

    public InvoiceRunProgress(Integer numberOfInvoicesToGenerate) {
        this.numberOfInvoicesToGenerate = numberOfInvoicesToGenerate;
        this.numberOfSuccesses = 0;
        this.numberOfFailures = 0;
    }

    public void incrementSuccesses() {
        numberOfSuccesses++;
    }

    public void incrementFailure() {
        numberOfFailures++;
    }

    public Integer getNumberOfInvoicesToGenerate() {
        return numberOfInvoicesToGenerate;
    }

    public void setNumberOfInvoicesToGenerate(Integer numberOfInvoicesToGenerate) {
        this.numberOfInvoicesToGenerate = numberOfInvoicesToGenerate;
    }

    public Integer getNumberOfSuccesses() {
        return numberOfSuccesses;
    }

    public void setNumberOfSuccesses(Integer numberOfSuccesses) {
        this.numberOfSuccesses = numberOfSuccesses;
    }

    public Integer getNumberOfFailures() {
        return numberOfFailures;
    }

    public void setNumberOfFailures(Integer numberOfFailures) {
        this.numberOfFailures = numberOfFailures;
    }
}
