package com.softwarewithpassion.nrgyinvoicr.backend.readings.control.files;

import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.softwarewithpassion.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_OFFSET_DATE_TIME_LESS_PRECISION;

@Component
class ReadingLineParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_OFFSET_DATE_TIME_LESS_PRECISION);

    ReadingValue parse(String line, int lineNumber, Long readingId) throws ReadingException {
        validateEmptyLine(lineNumber, line);
        String[] values = splitLine(lineNumber, line);
        String dateAsString = values[0];
        ZonedDateTime date = parseDate(lineNumber, dateAsString);
        String valueAsString = values[1];
        BigDecimal value = parseValue(lineNumber, valueAsString);
        return new ReadingValue(readingId, date, value);
    }

    private void validateEmptyLine(int lineNumber, String line) throws EmptyReadingLineException {
        if (line == null || line.isEmpty()) {
            throw new EmptyReadingLineException(lineNumber);
        }
    }

    private String[] splitLine(int lineNumber, String line) throws InvalidNumberOfValuesInReadingLine {
        String[] values = line.split(";");
        if (values.length != 2) {
            throw new InvalidNumberOfValuesInReadingLine(lineNumber, values.length);
        }
        return values;
    }

    private ZonedDateTime parseDate(int lineNumber, String readingDateAsString) throws InvalidDateInReadingLine {
        ZonedDateTime date;
        try {
            date = ZonedDateTime.parse(readingDateAsString, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateInReadingLine(lineNumber, readingDateAsString);
        }
        return date;
    }

    private BigDecimal parseValue(int lineNumber, String valueAsString) throws InvalidReadingValueInReadingLine {
        BigDecimal value;
        try {
            value = new BigDecimal(valueAsString);
        } catch (NumberFormatException e) {
            throw new InvalidReadingValueInReadingLine(lineNumber, valueAsString);
        }
        return value;
    }
}
