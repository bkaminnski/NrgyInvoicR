package com.hclc.nrgyinvoicr.backend.readings;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@IdClass(ReadingPrimaryKey.class)
public class Reading {

    @NotNull
    @Column
    @Id
    private Long meterId;

    @NotNull
    @Temporal(TIMESTAMP)
    @Id
    private Date readingDate;

    @Column
    private BigDecimal value;

    public Reading() {
    }

    public Reading(@NotNull Long meterId, @NotNull Date readingDate, BigDecimal value) {
        this.meterId = meterId;
        this.readingDate = readingDate;
        this.value = value;
    }

    public Long getMeterId() {
        return meterId;
    }

    public Date getReadingDate() {
        return readingDate;
    }

    public BigDecimal getValue() {
        return value;
    }
}
