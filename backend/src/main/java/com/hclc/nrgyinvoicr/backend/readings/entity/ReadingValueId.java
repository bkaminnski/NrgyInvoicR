package com.hclc.nrgyinvoicr.backend.readings.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class ReadingValueId implements Serializable {
    private Long readingId;
    private ZonedDateTime date;
}
