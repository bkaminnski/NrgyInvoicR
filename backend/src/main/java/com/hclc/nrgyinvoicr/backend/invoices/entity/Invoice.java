package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;
import com.hclc.nrgyinvoicr.backend.clients.entity.Client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Invoice extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "invoice_id_seq", sequenceName = "invoice_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "invoice_id_seq")
    private Long id;

    @NotNull
    @Column(length = 255)
    private String number;

    @NotNull
    private ZonedDateTime issueDate;

    @Column(name = "invoice_run_id")
    private Long invoiceRunId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @NotNull
    private BigDecimal grossTotal;

    @OneToMany
    @JoinColumn(name = "invoice_id")
    private List<InvoiceLine> invoiceLines;

    protected Invoice() {
    }

    public Invoice(String number, ZonedDateTime issueDate, Long invoiceRunId, Client client, BigDecimal grossTotal) {
        this.number = number;
        this.issueDate = issueDate;
        this.invoiceRunId = invoiceRunId;
        this.client = client;
        this.grossTotal = grossTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ZonedDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(ZonedDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }
}
