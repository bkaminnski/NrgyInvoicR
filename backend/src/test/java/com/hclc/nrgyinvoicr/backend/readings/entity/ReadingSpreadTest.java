package com.hclc.nrgyinvoicr.backend.readings.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class ReadingSpreadTest {

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("parameters")
    void shouldProperlyCalculateNumberOfExpectedReadings(String description, String firstReadingDateAsString, String lastReadingDateAsString, long numberOfExpectedReadings) {
        ZonedDateTime firstReadingDate = ZonedDateTime.parse(firstReadingDateAsString);
        ZonedDateTime lastReadingDate = ZonedDateTime.parse(lastReadingDateAsString);

        ReadingSpread readingSpread = new ReadingSpread(firstReadingDate, lastReadingDate, 1);

        assertThat(readingSpread.getNumberOfExpectedValues()).isEqualTo(numberOfExpectedReadings);
    }

    private static Stream<Arguments> parameters() {
        return Stream.of(
                of("1 reading", "2019-10-27T00:00+02:00", "2019-10-27T00:00+02:00", 1L),
                of("4 readings", "2019-10-27T00:00+02:00", "2019-10-27T00:45+02:00", 4L),
                of("16 readings within summer time", "2019-10-26T00:00+02:00", "2019-10-26T03:45+02:00", 16L),
                of("20 readings summer to within time", "2019-10-27T00:00+02:00", "2019-10-27T03:45+01:00", 20L)
        );
    }
}