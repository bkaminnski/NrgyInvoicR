package com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets;

import java.math.BigDecimal;

public class FlattenedBucket {
    private String dateSince;
    private String dateUntil;
    private Integer dayOfWeekSince;
    private Integer dayOfWeekUntil;
    private Integer hourSince;
    private Integer hourUntil;
    private final BigDecimal price;
    private final BigDecimal totalUsage;
    private final BigDecimal totalPrice;

    FlattenedBucket(UnconditionalBucket unconditionalBucket) {
        price = unconditionalBucket.getPrice();
        totalUsage = unconditionalBucket.getTotalUsage();
        totalPrice = unconditionalBucket.getTotalPrice();
    }

    FlattenedBucket(HourBucket hourBucket) {
        price = hourBucket.getPrice();
        totalUsage = hourBucket.getTotalUsage();
        totalPrice = hourBucket.getTotalPrice();
        hourSince = hourBucket.getSince();
        hourUntil = hourBucket.getUntil();
    }

    FlattenedBucket accept(DayOfWeekBucket dayOfWeekBucket) {
        dayOfWeekSince = dayOfWeekBucket.getSince();
        dayOfWeekUntil = dayOfWeekBucket.getUntil();
        return this;
    }

    FlattenedBucket accept(DateBucket dateBucket) {
        dateSince = dateBucket.getSinceAsString();
        dateUntil = dateBucket.getUntilAsString();
        return this;
    }

    public String getDescription() {
        StringBuffer description = new StringBuffer();
        if (dateSince != null && dateUntil != null) {
            appendDatePeriod(description);
        }
        if (dayOfWeekSince != null && dayOfWeekUntil != null) {
            appendSeparator(description);
            appendDayOfWeekPeriod(description);
        }
        if (hourSince != null && hourUntil != null) {
            appendSeparator(description);
            appendHourPeriod(description);
        }
        appendStableDescription(description);
        return description.toString();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotalUsage() {
        return totalUsage;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    private void appendDatePeriod(StringBuffer description) {
        description.append(dateSince).append(" - ").append(dateUntil);
    }

    private void appendSeparator(StringBuffer description) {
        if (description.length() > 0) {
            description.append(", ");
        }
    }

    private void appendDayOfWeekPeriod(StringBuffer description) {
        description
                .append(dayOfWeekToName(dayOfWeekSince))
                .append(" - ")
                .append(dayOfWeekToName(dayOfWeekUntil));
    }

    private String dayOfWeekToName(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            case 7:
                return "Sunday";
            default:
                return "";
        }
    }

    private void appendHourPeriod(StringBuffer description) {
        description
                .append(hourSince)
                .append("h - ")
                .append(hourUntil)
                .append("h");
    }

    private void appendStableDescription(StringBuffer description) {
        if (description.length() == 0) {
            description.append("Stable");
        }
    }
}
