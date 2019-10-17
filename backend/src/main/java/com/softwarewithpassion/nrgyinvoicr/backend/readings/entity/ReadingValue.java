package com.softwarewithpassion.nrgyinvoicr.backend.readings.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@IdClass(ReadingValueId.class)
public class ReadingValue {

    @NotNull
    @Column
    @Id
    private Long readingId;

    @NotNull
    @Id
    private ZonedDateTime date;

    @Column
    private BigDecimal value;

    public ReadingValue() {
    }

    public ReadingValue(@NotNull Long readingId, @NotNull ZonedDateTime date, BigDecimal value) {
        this.readingId = readingId;
        this.date = date;
        this.value = value;
    }

    public Long getReadingId() {
        return readingId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ReadingValue atTimeZone(ZoneId zoneId) {
        return new ReadingValue(readingId, date.withZoneSameInstant(zoneId), value);
    }
}
