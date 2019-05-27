package com.hclc.nrgyinvoicr.backend.readings.control.files;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.of;

class ReadingValueLineParserTest {
    private ReadingLineParser readingLineParser;

    @BeforeEach
    void beforeEach() {
        this.readingLineParser = new ReadingLineParser();
    }

    @Test
    void whenLineIsCorrect_shouldParseLine() throws ReadingException {
        String line = "2019-10-27T00:00+02:00;123.45";

        ReadingValue readingValue = readingLineParser.parse(line, 2, 1L);

        assertThat(readingValue.getReadingId()).isEqualTo(1L);
        assertThat(readingValue.getDate()).isEqualTo("2019-10-27T00:00+02:00");
        assertThat(readingValue.getValue()).isEqualByComparingTo(new BigDecimal("123.45"));
    }

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("parameters")
    void whenLineIsIncorrect_shouldThrowException(String description, String line, String expectedMessage) {
        assertThatThrownBy(() -> readingLineParser.parse(line, 2, 1L)).hasMessage(expectedMessage);
    }

    private static Stream<Arguments> parameters() {
        return Stream.of(
                of("Line is null", null, "Line 2 is empty."),
                of("Line is empty", "", "Line 2 is empty."),
                of("Line has too little values", "xyz", "Line 2 has 1 value(s). Line should have exactly two values."),
                of("Line has too many values", "xyz;abc;def", "Line 2 has 3 value(s). Line should have exactly two values."),
                of("Invalid date", "2019-27-10T00:00+02:00;123.45", "Invalid date in line 2: 2019-27-10T00:00+02:00. A date should match the following pattern: yyyy-MM-dd'T'HH:mmZZZZZ."),
                of("Invalid value", "2019-10-27T00:00+02:00;123ABC", "Invalid numeric value in line 2: 123ABC.")
        );
    }
}