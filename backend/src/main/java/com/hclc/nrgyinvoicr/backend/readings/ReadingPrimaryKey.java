package com.hclc.nrgyinvoicr.backend.readings;

import java.io.Serializable;
import java.util.Date;

public class ReadingPrimaryKey implements Serializable {
    private Long meterId;
    private Date readingDate;
}
