package com.hclc.nrgyinvoicr.backend.readings.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Duration;
import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

@Embeddable
public class ReadingSpread {
    private static final long SECONDS_IN_MINUTE = 60L;
    private static final long READINGS_INTERVAL_IN_MINUTES = 15L;

    @Column
    private ZonedDateTime sinceClosed;

    @Column
    private ZonedDateTime untilOpen;

    @Column
    private long numberOfMadeReadings;

    @Column
    private long numberOfExpectedReadings;

    public ReadingSpread() {
    }

    public ReadingSpread(ZonedDateTime firstReadingDate, ZonedDateTime lastReadingDate, long numberOfMadeReadings) {
        this.sinceClosed = firstReadingDate;
        this.untilOpen = lastReadingDate.plus(READINGS_INTERVAL_IN_MINUTES, MINUTES);
        this.numberOfMadeReadings = numberOfMadeReadings;
        this.numberOfExpectedReadings = calculateNumberOfIntervalsInDuration(sinceClosed, untilOpen);
    }

    private long calculateNumberOfIntervalsInDuration(ZonedDateTime sinceClosed, ZonedDateTime untilOpen) {
        return Duration.between(sinceClosed, untilOpen).getSeconds() / SECONDS_IN_MINUTE / READINGS_INTERVAL_IN_MINUTES;
    }

    public ZonedDateTime getSinceClosed() {
        return sinceClosed;
    }

    public ZonedDateTime getUntilOpen() {
        return untilOpen;
    }

    public long getNumberOfMadeReadings() {
        return numberOfMadeReadings;
    }

    public long getNumberOfExpectedReadings() {
        return numberOfExpectedReadings;
    }
}
