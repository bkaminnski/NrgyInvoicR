package com.hclc.nrgyinvoicr.backend.readings;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@IdClass(ReadingId.class)
public class Reading {

    @NotNull
    @Column
    @Id
    private Long importId;

    @NotNull
    @Temporal(TIMESTAMP)
    @Id
    private Date date;

    @Column
    private BigDecimal value;

    public Reading() {
    }

    public Reading(@NotNull Long importId, @NotNull Date date, BigDecimal value) {
        this.importId = importId;
        this.date = date;
        this.value = value;
    }

    public Long getImportId() {
        return importId;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }
}
