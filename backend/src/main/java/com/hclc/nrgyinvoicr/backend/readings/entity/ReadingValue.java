package com.hclc.nrgyinvoicr.backend.readings.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@IdClass(ReadingValueId.class)
public class ReadingValue {

    @NotNull
    @Column
    @Id
    private Long readingId;

    @NotNull
    @Temporal(TIMESTAMP)
    @Id
    private Date date;

    @Column
    private BigDecimal value;

    public ReadingValue() {
    }

    public ReadingValue(@NotNull Long readingId, @NotNull Date date, BigDecimal value) {
        this.readingId = readingId;
        this.date = date;
        this.value = value;
    }

    public Long getReadingId() {
        return readingId;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }
}
