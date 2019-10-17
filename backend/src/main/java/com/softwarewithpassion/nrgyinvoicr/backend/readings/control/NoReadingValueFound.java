package com.softwarewithpassion.nrgyinvoicr.backend.readings.control;

import com.softwarewithpassion.nrgyinvoicr.backend.meters.entity.Meter;

import java.time.ZonedDateTime;

import static com.softwarewithpassion.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;

public class NoReadingValueFound extends Exception {
    NoReadingValueFound(ZonedDateTime sinceClosed, ZonedDateTime untilOpen, Meter meter) {
        super("No reading value was found for a meter " + meter.getSerialNumber()
                + " for dates between " + sinceClosed.format(ofPattern(ISO_8601_DATE))
                + " and " + untilOpen.format(ofPattern(ISO_8601_DATE)));
    }
}

