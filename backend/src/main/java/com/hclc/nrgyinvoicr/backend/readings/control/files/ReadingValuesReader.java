package com.hclc.nrgyinvoicr.backend.readings.control.files;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingSpread;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

import java.io.*;
import java.time.ZonedDateTime;

public class ReadingValuesReader implements Closeable {
    private final Long readingId;
    private final BufferedReader bufferedReader;
    private final ReadingLineParser readingLineParser;
    private int lineNumber = 0;
    private ZonedDateTime firstReadingDate = null;
    private ZonedDateTime lastReadingDate = null;

    ReadingValuesReader(InputStream fileContent, ReadingLineParser readingLineParser, Long readingId) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(fileContent));
        this.readingLineParser = readingLineParser;
        this.readingId = readingId;
    }

    ReadingValue readReadingValue() throws IOException, ReadingException {
        String line = bufferedReader.readLine();
        if (line == null) {
            return null;
        }
        lineNumber++;
        ReadingValue readingValue = readingLineParser.parse(line, lineNumber, readingId);
        keepFirstAndLastReading(readingValue.getDate());
        return readingValue;
    }

    private void keepFirstAndLastReading(ZonedDateTime readingDate) {
        if (firstReadingDate == null) {
            firstReadingDate = readingDate;
        }
        lastReadingDate = readingDate;
    }

    ReadingSpread determineReadingSpread() {
        return new ReadingSpread(firstReadingDate, lastReadingDate, lineNumber);
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}
