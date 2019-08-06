package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class InvoiceRun extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "invoice_run_id_seq", sequenceName = "invoice_run_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "invoice_run_id_seq")
    private Long id;

    @NotNull
    private ZonedDateTime sinceClosed;

    @NotNull
    private ZonedDateTime untilOpen;

    @NotNull
    private ZonedDateTime issueDate;

    @NotNull
    private String numberTemplate;

    @NotNull
    private Integer firstInvoiceNumber;

    public String format(Integer invoiceNumber) {
        return String.format(numberTemplate, invoiceNumber);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSinceClosed() {
        return sinceClosed;
    }

    public void setSinceClosed(ZonedDateTime sinceClosed) {
        this.sinceClosed = sinceClosed;
    }

    public ZonedDateTime getUntilOpen() {
        return untilOpen;
    }

    public void setUntilOpen(ZonedDateTime untilOpen) {
        this.untilOpen = untilOpen;
    }

    public ZonedDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(ZonedDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public String getNumberTemplate() {
        return numberTemplate;
    }

    public void setNumberTemplate(String numberTemplate) {
        this.numberTemplate = numberTemplate;
    }

    public Integer getFirstInvoiceNumber() {
        return firstInvoiceNumber;
    }

    public void setFirstInvoiceNumber(Integer firstInvoiceNumber) {
        this.firstInvoiceNumber = firstInvoiceNumber;
    }
}
