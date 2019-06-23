package com.hclc.nrgyinvoicr.backend.plans.entity.buckets;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class ExpressionLineTest {

    @ParameterizedTest(name = "{index} {0}")
    @MethodSource("parameters")
    void shouldParseLine(String description, String line, int level, String rangeStart, String rangeEnd, BigDecimal value) {
        ExpressionLine expressionLine = new ExpressionLine(0, line);

        assertThat(expressionLine.isLevel(level - 1)).isEqualTo(false);
        assertThat(expressionLine.isLevel(level)).isEqualTo(true);
        assertThat(expressionLine.isLevel(level + 1)).isEqualTo(false);
        assertThat(expressionLine.getRangeStart()).isEqualTo(rangeStart);
        assertThat(expressionLine.getRangeEnd()).isEqualTo(rangeEnd);
        assertThat(expressionLine.getValue()).isEqualTo(value);
    }

    private static Stream<Arguments> parameters() {
        return Stream.of(
                of("First level line with value", ".01.01-12.31:123.45", 1, "01.01", "12.31", new BigDecimal("123.45")),
                of("First level line without value", ".01.01-12.31", 1, "01.01", "12.31", null),
                of("Second level line with value", "..1-5:123.45", 2, "1", "5", new BigDecimal("123.45")),
                of("Second level line without value", "..1-5", 2, "1", "5", null),
                of("Third level line with value", "...0-23:123.45", 3, "0", "23", new BigDecimal("123.45")),
                of("Third level line without value", "...0-23", 3, "0", "23", null)
        );
    }
}