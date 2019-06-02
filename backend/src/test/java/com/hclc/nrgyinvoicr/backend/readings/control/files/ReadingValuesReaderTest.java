package com.hclc.nrgyinvoicr.backend.readings.control.files;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingSpread;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Java6Assertions.assertThat;

class ReadingValuesReaderTest {

    @Test
    void whenAllReadingValuesAreRead_shouldProperlyDetermineReadingSpread() throws IOException, ReadingException {
        InputStream inputStream = this.getClass().getResourceAsStream("/meter_reading-2_values-correct.csv");

        try (ReadingValuesReader readingValuesReader = new ReadingValuesReader(inputStream, new ReadingLineParser(), 1L)) {
            readAllReadingValues(readingValuesReader);
            ReadingSpread readingSpread = readingValuesReader.determineReadingSpread();

            assertThat(readingSpread.getNumberOfMeasuredValues()).isEqualTo(2L);
            assertThat(readingSpread.getNumberOfExpectedValues()).isEqualTo(2L);
            assertThat(readingSpread.getSinceClosed()).isEqualByComparingTo(ZonedDateTime.parse("2019-10-27T00:00+02:00"));
            assertThat(readingSpread.getUntilOpen()).isEqualByComparingTo(ZonedDateTime.parse("2019-10-27T00:30+02:00"));
        }
    }

    private void readAllReadingValues(ReadingValuesReader readingValuesReader) throws IOException, ReadingException {
        while (readingValuesReader.readReadingValue() != null) {
            // read each value
        }
    }
}