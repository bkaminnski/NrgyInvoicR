package com.softwarewithpassion.nrgyinvoicr.backend.readings.entity;

import com.softwarewithpassion.nrgyinvoicr.backend.AuditableEntity;
import com.softwarewithpassion.nrgyinvoicr.backend.meters.entity.Meter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
public class Reading extends AuditableEntity {

    @Id
    @SequenceGenerator(name = "reading_id_seq", sequenceName = "reading_id_seq", initialValue = 1, allocationSize = 50)
    @GeneratedValue(strategy = SEQUENCE, generator = "reading_id_seq")
    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @Embedded
    private ReadingSpread readingSpread;

    public Reading() {
    }

    public Reading(@NotNull LocalDate date, @NotNull Meter meter) {
        this.date = date;
        this.meter = meter;
    }

    public Reading updatedWithReadingsSpread(ReadingSpread readingSpread) {
        this.readingSpread = readingSpread;
        return this;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Meter getMeter() {
        return meter;
    }

    public ReadingSpread getReadingSpread() {
        return readingSpread;
    }
}
