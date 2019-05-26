package com.hclc.nrgyinvoicr.backend.readings;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import java.time.Duration;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Embeddable
public class ReadingsSpread {
    private static final long SECONDS_IN_MINUTE = 60L;
    private static final long READINGS_INTERVAL_IN_MINUTES = 15L;

    @Temporal(TIMESTAMP)
    private Date readingsSinceClosed;

    @Temporal(TIMESTAMP)
    private Date readingsUntilOpen;

    @Column
    private long numberOfExpectedReadings;

    @Column
    private long numberOfImportedReadings;

    public ReadingsSpread() {
    }

    public ReadingsSpread(Date readingsSinceClosed, Date readingsUntilOpen, long numberOfImportedReadings) {
        this.readingsSinceClosed = readingsSinceClosed;
        this.readingsUntilOpen = readingsUntilOpen;
        this.numberOfExpectedReadings = calculateNumberOfIntervalsInDuration(readingsSinceClosed, readingsUntilOpen);
        this.numberOfImportedReadings = numberOfImportedReadings;
    }

    private long calculateNumberOfIntervalsInDuration(Date readingsSinceClosed, Date readingsUntilOpen) {
        return Duration.between(readingsSinceClosed.toInstant(), readingsUntilOpen.toInstant()).getSeconds() / SECONDS_IN_MINUTE / READINGS_INTERVAL_IN_MINUTES;
    }

    public Date getReadingsSinceClosed() {
        return readingsSinceClosed;
    }

    public Date getReadingsUntilOpen() {
        return readingsUntilOpen;
    }

    public long getNumberOfExpectedReadings() {
        return numberOfExpectedReadings;
    }

    public long getNumberOfImportedReadings() {
        return numberOfImportedReadings;
    }
}
