package com.hclc.nrgyinvoicr.backend.plans.control.expression;

import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.FlattenedBucket;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.LineError;
import com.hclc.nrgyinvoicr.backend.plans.control.expression.buckets.LineException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

class ExpressionParserTest {
    private static final String VALID_EXPRESSION = ".01.01-12.31\r\n..1-7\r\n...0-23:0.18692";
    private static final String LINE_ERROR_MISSING_DATE_RANGE_1 = null;
    private static final String LINE_ERROR_MISSING_DATE_RANGE_2 = "";
    private static final String LINE_ERROR_MISSING_DATE_RANGE_3 = "..1-7\r\n...0-23:0.18692";
    private static final String LINE_ERROR_DATE_MISSING_DAY_OF_WEEK_RANGE = ".01.01-12.31";
    private static final String LINE_ERROR_DATE_PRICE_DECLARED = ".01.01-12.31:0.18692\r\n..1-7\r\n...0-23";
    private static final String LINE_ERROR_DATE_START_1 = ".x.01-12.31\r\n..1-7\r\n...0-23:0.18692";
    private static final String LINE_ERROR_DATE_START_2 = ".01;01-12.31\r\n..1-7\r\n...0-23:0.18692";
    private static final String LINE_ERROR_DATE_END_1 = ".01.01-x\r\n..1-7\r\n...0-23:0.18692";
    private static final String LINE_ERROR_DATE_END_2 = ".01.01-12;31\r\n..1-7\r\n...0-23:0.18692";
    private static final String LINE_ERROR_DAY_OF_WEEK_MISSING_HOUR_RANGE = ".01.01-12.31\r\n..1-7";
    private static final String LINE_ERROR_DAY_OF_WEEK_PRICE_DECLARED = ".01.01-12.31\r\n..1-7:0.18692\r\n...0-23";
    private static final String LINE_ERROR_DAY_OF_WEEK_START_1 = ".01.01-12.31\r\n..x-7\r\n...0-23:0.18692";
    private static final String LINE_ERROR_DAY_OF_WEEK_START_2 = ".01.01-12.31\r\n..0-7\r\n...0-23:0.18692";
    private static final String LINE_ERROR_DAY_OF_WEEK_END_1 = ".01.01-12.31\r\n..1-x\r\n...0-23:0.18692";
    private static final String LINE_ERROR_DAY_OF_WEEK_END_2 = ".01.01-12.31\r\n..1-8\r\n...0-23:0.18692";
    private static final String LINE_ERROR_HOUR_MISSING_PRICE = ".01.01-12.31\r\n..1-7\r\n...0-23";
    private static final String LINE_ERROR_HOUR_START_1 = ".01.01-12.31\r\n..1-7\r\n...x-23:0.18692";
    private static final String LINE_ERROR_HOUR_START_2 = ".01.01-12.31\r\n..1-7\r\n...50-23:0.18692";
    private static final String LINE_ERROR_HOUR_END_1 = ".01.01-12.31\r\n..1-7\r\n...0-x:0.18692";
    private static final String LINE_ERROR_HOUR_END_2 = ".01.01-12.31\r\n..1-7\r\n...0-50:0.18692";
    private ExpressionParser expressionParser;

    @BeforeEach
    void setUp() {
        expressionParser = new ExpressionParser();
    }

    @Test
    void shouldParseExpression() throws IOException {
        List<FlattenedBucket> flattenedBuckets = expressionParser.parse(VALID_EXPRESSION).flatten();

        assertThat(flattenedBuckets).hasSize(1);
        assertThat(flattenedBuckets.get(0).getDescription()).isEqualTo("Stable");
        assertThat(flattenedBuckets.get(0).getPrice()).isEqualByComparingTo("0.18692");
        assertThat(flattenedBuckets.get(0).getTotalUsage()).isEqualByComparingTo(ZERO);
        assertThat(flattenedBuckets.get(0).getTotalPrice()).isEqualByComparingTo(ZERO);
    }


    @ParameterizedTest(name = "{0}")
    @MethodSource("errors")
    void shouldThrowException(String description, String expression, int expectedLineNumber, String expectedMessage) {
        assertThat(((LineException) catchThrowable(() -> expressionParser.parse(expression))).toLineError())
                .isEqualToComparingFieldByField(new LineError(expectedLineNumber, expectedMessage));
    }

    private static Stream<Arguments> errors() {
        return Stream.of(
                of("LINE_ERROR_MISSING_DATE_RANGE_1", LINE_ERROR_MISSING_DATE_RANGE_1, 1, "No date range was found. A date range should start with \".\"."),
                of("LINE_ERROR_MISSING_DATE_RANGE_2", LINE_ERROR_MISSING_DATE_RANGE_2, 1, "No date range was found. A date range should start with \".\"."),
                of("LINE_ERROR_MISSING_DATE_RANGE_3", LINE_ERROR_MISSING_DATE_RANGE_3, 1, "No date range was found. A date range should start with \".\"."),
                of("LINE_ERROR_DATE_MISSING_DAY_OF_WEEK_RANGE", LINE_ERROR_DATE_MISSING_DAY_OF_WEEK_RANGE, 1, "No day of week range was found for a date range. A day of week range should start with \"..\"."),
                of("LINE_ERROR_DATE_PRICE_DECLARED", LINE_ERROR_DATE_PRICE_DECLARED, 1, "Price cannot be declared for a date range: 0.18692."),
                of("LINE_ERROR_DATE_START_1", LINE_ERROR_DATE_START_1, 1, "Invalid start of date range: \"x.01\". Date should match a pattern \"MM.DD\"."),
                of("LINE_ERROR_DATE_START_2", LINE_ERROR_DATE_START_2, 1, "Invalid start of date range: \"01;01\". Date should match a pattern \"MM.DD\"."),
                of("LINE_ERROR_DATE_END_1", LINE_ERROR_DATE_END_1, 1, "Invalid end of date range: \"x\". Date should match a pattern \"MM.DD\"."),
                of("LINE_ERROR_DATE_END_2", LINE_ERROR_DATE_END_2, 1, "Invalid end of date range: \"12;31\". Date should match a pattern \"MM.DD\"."),
                of("LINE_ERROR_DAY_OF_WEEK_MISSING_HOUR_RANGE", LINE_ERROR_DAY_OF_WEEK_MISSING_HOUR_RANGE, 2, "No hour range was found for a day of week range. An hour range should start with \"...\"."),
                of("LINE_ERROR_DAY_OF_WEEK_PRICE_DECLARED", LINE_ERROR_DAY_OF_WEEK_PRICE_DECLARED, 2, "Price cannot be declared for a day of week range: 0.18692."),
                of("LINE_ERROR_DAY_OF_WEEK_START_1", LINE_ERROR_DAY_OF_WEEK_START_1, 2, "Invalid start of day of week range: \"x\". The value should be a number between 1 (Monday) and 7 (Sunday)."),
                of("LINE_ERROR_DAY_OF_WEEK_START_2", LINE_ERROR_DAY_OF_WEEK_START_2, 2, "Invalid start of day of week range: \"0\". The value should be a number between 1 (Monday) and 7 (Sunday)."),
                of("LINE_ERROR_DAY_OF_WEEK_END_1", LINE_ERROR_DAY_OF_WEEK_END_1, 2, "Invalid end of day of week range: \"x\". The value should be a number between 1 (Monday) and 7 (Sunday)."),
                of("LINE_ERROR_DAY_OF_WEEK_END_2", LINE_ERROR_DAY_OF_WEEK_END_2, 2, "Invalid end of day of week range: \"8\". The value should be a number between 1 (Monday) and 7 (Sunday)."),
                of("LINE_ERROR_HOUR_MISSING_PRICE", LINE_ERROR_HOUR_MISSING_PRICE, 3, "Price is missing in hour range."),
                of("LINE_ERROR_HOUR_START_1", LINE_ERROR_HOUR_START_1, 3, "Invalid start of hour range: \"x\". The value should be a number between 0 (inclusive) and 23 (inclusive)."),
                of("LINE_ERROR_HOUR_START_2", LINE_ERROR_HOUR_START_2, 3, "Invalid start of hour range: \"50\". The value should be a number between 0 (inclusive) and 23 (inclusive)."),
                of("LINE_ERROR_HOUR_END_1", LINE_ERROR_HOUR_END_1, 3, "Invalid end of hour range: \"x\". The value should be a number between 0 (inclusive) and 23 (inclusive)."),
                of("LINE_ERROR_HOUR_END_2", LINE_ERROR_HOUR_END_2, 3, "Invalid end of hour range: \"50\". The value should be a number between 0 (inclusive) and 23 (inclusive).")
        );
    }
}