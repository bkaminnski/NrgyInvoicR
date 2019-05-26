package com.hclc.nrgyinvoicr.backend.readings;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;

@Component
class FileNameParser {
    private static final String FILE_NAME_REGEX = "mr_([a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})_(\\d{4}-\\d{2}-\\d{2})_(\\d{3})\\.csv";
    private static final Pattern fileNamePattern = Pattern.compile(FILE_NAME_REGEX);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_DATE);

    /**
     * @param fileName For example: "mr_18f026ab-552a-4963-b058-57eae0c5ce59_20190521_001.csv";
     */
    ParsedFileName parse(String fileName) throws FileNameParsingException {
        Matcher matcher = findGroups(fileName);
        String meterId = matcher.group(1);
        String readingDateAsString = matcher.group(2);
        Date readingDate = parseDate(readingDateAsString);
        return new ParsedFileName(meterId, readingDate);
    }

    private Matcher findGroups(String fileName) throws FileNameParsingException {
        Matcher matcher = fileNamePattern.matcher(fileName);
        if (!matcher.matches()) {
            throw new FileNameParsingException("Invalid file name: " + fileName + ". A file name should match the following pattern: mr_[meter UUID]_[reading date " + ISO_8601_DATE + "]_[sequence number].csv.");
        }
        return matcher;
    }

    private Date parseDate(String readingDateAsString) throws FileNameParsingException {
        try {
            return Date.from(LocalDate.parse(readingDateAsString, formatter).atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            throw new FileNameParsingException("Invalid reading date in the file name: " + readingDateAsString + ". A date should match the following pattern: " + ISO_8601_DATE + ".");
        }
    }
}
