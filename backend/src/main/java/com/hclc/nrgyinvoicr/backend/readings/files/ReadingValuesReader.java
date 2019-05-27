package com.hclc.nrgyinvoicr.backend.readings.files;

import com.hclc.nrgyinvoicr.backend.readings.ReadingSpread;
import com.hclc.nrgyinvoicr.backend.readings.ReadingValue;

import java.io.*;
import java.util.Date;

public class ReadingValuesReader implements Closeable {
    private final Long readingId;
    private final BufferedReader bufferedReader;
    private final ReadingLineParser readingLineParser;
    private int lineNumber = 0;
    private Date firstReadingDate = null;
    private Date lastReadingDate = null;

    ReadingValuesReader(InputStream fileContent, ReadingLineParser readingLineParser, Long readingId) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(fileContent));
        this.readingLineParser = readingLineParser;
        this.readingId = readingId;
    }

    ReadingValue readReadingValue() throws IOException, ReadingException {
        lineNumber++;
        String line = bufferedReader.readLine();
        if (line == null) {
            return null;
        }
        ReadingValue readingValue = readingLineParser.parse(line, lineNumber, readingId);
        keepFirstAndLastReading(readingValue.getDate());
        return readingValue;
    }

    private void keepFirstAndLastReading(Date readingDate) {
        if (firstReadingDate == null) {
            firstReadingDate = readingDate;
        }
        lastReadingDate = readingDate;
    }

    ReadingSpread determineReadingsSpread() {
        return new ReadingSpread(firstReadingDate, lastReadingDate, lineNumber);
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}
