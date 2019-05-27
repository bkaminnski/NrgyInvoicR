package com.hclc.nrgyinvoicr.backend.readings.control.files;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_OFFSET_DATE_TIME_LESS_PRECISION;

@Component
class ReadingLineParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_OFFSET_DATE_TIME_LESS_PRECISION);

    ReadingValue parse(String line, int lineNumber, Long readingId) throws ReadingException {
        validateEmptyLine(lineNumber, line);
        String[] values = splitLine(lineNumber, line);
        String dateAsString = values[0];
        Date date = parseDate(lineNumber, dateAsString);
        String valueAsString = values[1];
        BigDecimal value = parseValue(lineNumber, valueAsString);
        return new ReadingValue(readingId, date, value);
    }

    private void validateEmptyLine(int lineNumber, String line) throws ReadingException {
        if (line == null || line.isEmpty()) {
            throw new ReadingException("Line " + lineNumber + " is empty.");
        }
    }

    private String[] splitLine(int lineNumber, String line) throws ReadingException {
        String[] values = line.split(";");
        if (values.length != 2) {
            throw new ReadingException("Line " + lineNumber + " has " + values.length + " value(s). Line should have exactly two values.");
        }
        return values;
    }

    private Date parseDate(int lineNumber, String readingDateAsString) throws ReadingException {
        Date date;
        try {
            date = Date.from(ZonedDateTime.parse(readingDateAsString, formatter).toInstant());
        } catch (DateTimeParseException e) {
            throw new ReadingException("Invalid date in line " + lineNumber + ": " + readingDateAsString + ". A date should match the following pattern: " + ISO_8601_OFFSET_DATE_TIME_LESS_PRECISION + ".");
        }
        return date;
    }

    private BigDecimal parseValue(int lineNumber, String valueAsString) throws ReadingException {
        BigDecimal value;
        try {
            value = new BigDecimal(valueAsString);
        } catch (NumberFormatException e) {
            throw new ReadingException("Invalid numeric value in line " + lineNumber + ": " + valueAsString + ".");
        }
        return value;
    }
}
