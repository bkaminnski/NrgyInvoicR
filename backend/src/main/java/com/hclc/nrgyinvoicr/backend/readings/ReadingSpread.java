package com.hclc.nrgyinvoicr.backend.readings;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import java.time.Duration;
import java.util.Date;

import static java.time.temporal.ChronoUnit.MINUTES;
import static javax.persistence.TemporalType.TIMESTAMP;

@Embeddable
public class ReadingSpread {
    private static final long SECONDS_IN_MINUTE = 60L;
    private static final long READINGS_INTERVAL_IN_MINUTES = 15L;

    @Temporal(TIMESTAMP)
    private Date sinceClosed;

    @Temporal(TIMESTAMP)
    private Date untilOpen;

    @Column
    private long numberOfMadeReadings;

    @Column
    private long numberOfExpectedReadings;

    public ReadingSpread() {
    }

    public ReadingSpread(Date firstReadingDate, Date lastReadingDate, long numberOfMadeReadings) {
        this.sinceClosed = firstReadingDate;
        this.untilOpen = Date.from(lastReadingDate.toInstant().plus(READINGS_INTERVAL_IN_MINUTES, MINUTES));
        this.numberOfMadeReadings = numberOfMadeReadings;
        this.numberOfExpectedReadings = calculateNumberOfIntervalsInDuration(sinceClosed, untilOpen);
    }

    private long calculateNumberOfIntervalsInDuration(Date sinceClosed, Date untilOpen) {
        return Duration.between(sinceClosed.toInstant(), untilOpen.toInstant()).getSeconds() / SECONDS_IN_MINUTE / READINGS_INTERVAL_IN_MINUTES;
    }
}
