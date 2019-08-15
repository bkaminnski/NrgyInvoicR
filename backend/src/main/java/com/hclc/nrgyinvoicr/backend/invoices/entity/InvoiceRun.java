package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hclc.nrgyinvoicr.backend.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

import static com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunStatus.NEW;
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

    @Enumerated(EnumType.STRING)
    @Column
    private InvoiceRunStatus status = NEW;

    @Embedded
    private InvoiceRunProgress progress;

    @OneToMany
    @JoinColumn(name = "invoice_run_id")
    @JsonIgnore
    private List<InvoiceRunMessage> invoiceRunMessages;

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

    public InvoiceRunStatus getStatus() {
        return status;
    }

    public void setStatus(InvoiceRunStatus status) {
        this.status = status;
    }

    public InvoiceRunProgress getProgress() {
        return progress;
    }

    public void setProgress(InvoiceRunProgress progress) {
        this.progress = progress;
    }

    public List<InvoiceRunMessage> getInvoiceRunMessages() {
        return invoiceRunMessages;
    }

    public void setInvoiceRunMessages(List<InvoiceRunMessage> invoiceRunMessages) {
        this.invoiceRunMessages = invoiceRunMessages;
    }
}