package com.softwarewithpassion.nrgyinvoicr.backend.plans.control;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.ExpressionTestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ExpressionTesterTest {
    private static final String VALID_EXPRESSION = ".01.01-12.31\r\n..1-7\r\n...0-23:0.18692";
    private static final String INVALID_EXPRESSION = ".x.01-12.31\r\n..1-7\r\n...0-23:0.18692";
    private static final String LEAKY_EXPRESSION = ".01.01-12.31\r\n..1-5\r\n...0-23:0.18692";
    private ExpressionTester expressionTester;

    @BeforeEach
    void setUp() {
        expressionTester = new ExpressionTester();
    }

    @Test
    void whenExpressionIsValid_shouldReturnValidTestResult() throws IOException {
        ExpressionTestResult testResult = expressionTester.test(VALID_EXPRESSION);

        assertThat(testResult.isValid()).isTrue();
        assertThat(testResult.getCoversWholeYear()).isTrue();
        assertThat(testResult.getFlattenedBuckets()).hasSize(1);
        assertThat(testResult.getLineError()).isNull();
    }

    @Test
    void whenExpressionIsInvalid_shouldReturnInvalidTestResult() throws IOException {
        ExpressionTestResult testResult = expressionTester.test(INVALID_EXPRESSION);

        assertThat(testResult.isValid()).isFalse();
        assertThat(testResult.getCoversWholeYear()).isNull();
        assertThat(testResult.getFlattenedBuckets()).isNull();
        assertThat(testResult.getLineError()).isNotNull();
        assertThat(testResult.getLineError().getLineNumber()).isEqualTo(1);
        assertThat(testResult.getLineError().getErrorMessage()).isNotBlank();
    }

    @Test
    void whenExpressionDoesNotCoverFullYear_shouldReturnInvalidTestResult() throws IOException {
        ExpressionTestResult testResult = expressionTester.test(LEAKY_EXPRESSION);

        assertThat(testResult.isValid()).isFalse();
        assertThat(testResult.getCoversWholeYear()).isFalse();
        assertThat(testResult.getFlattenedBuckets()).hasSize(1);
        assertThat(testResult.getLineError()).isNull();
    }
}