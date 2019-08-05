package com.hclc.nrgyinvoicr.backend.invoices.entity;

import com.hclc.nrgyinvoicr.backend.AuditableEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class InvoiceLine extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "invoice_line_id_seq", sequenceName = "invoice_line_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "invoice_line_id_seq")
    private Long id;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @NotNull
    @Column(length = 255)
    private String description;

    @NotNull
    private BigDecimal unitPrice;

    @NotNull
    private BigDecimal quantity;

    @Enumerated(STRING)
    private Unit unit;

    @NotNull
    private BigDecimal netTotal;

    @NotNull
    private BigDecimal vat;

    @NotNull
    private BigDecimal grossTotal;

    protected InvoiceLine() {
    }

    public InvoiceLine(String description, BigDecimal unitPrice, BigDecimal quantity, Unit unit, BigDecimal vat) {
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.unit = unit;
        this.vat = vat;
        this.netTotal = unitPrice.multiply(quantity);
        this.grossTotal = netTotal.multiply(ONE.add(vat));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public BigDecimal getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(BigDecimal netTotal) {
        this.netTotal = netTotal;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(BigDecimal grossTotal) {
        this.grossTotal = grossTotal;
    }
}
