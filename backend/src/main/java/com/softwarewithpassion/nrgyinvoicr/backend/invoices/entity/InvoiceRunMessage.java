package com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class InvoiceRunMessage {

    @Id
    @SequenceGenerator(name = "invoice_run_message_id_seq", sequenceName = "invoice_run_message_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "invoice_run_message_id_seq")
    private Long id;

    @Column(name = "invoice_run_id")
    private Long invoiceRunId;

    @NotNull
    @Column(length = 2000)
    private String message;

    InvoiceRunMessage() {
    }

    public InvoiceRunMessage(InvoiceRun invoiceRun, String message) {
        this.invoiceRunId = invoiceRun.getId();
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceRunId() {
        return invoiceRunId;
    }

    public void setInvoiceRunId(Long invoiceRunId) {
        this.invoiceRunId = invoiceRunId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
