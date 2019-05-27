package com.hclc.nrgyinvoicr.backend.readings.files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileNameParserTest {
    private FileNameParser fileNameParser;

    @BeforeEach
    void beforeEach() {
        this.fileNameParser = new FileNameParser();
    }

    @Test
    void whenFileNameIsCorrect_shouldParseFileName() throws ReadingException {
        ParsedFileName parsedFileName = fileNameParser.parse("mr_18f026ab-552a-4963-b058-57eae0c5ce59_2019-05-21_001.csv");

        assertThat(parsedFileName.getMeterId()).isEqualTo("18f026ab-552a-4963-b058-57eae0c5ce59");
        assertThat(parsedFileName.getReadingDate()).isEqualTo("2019-05-21");
    }

    @Test
    void whenFileNameDoesNotMatchExpectedPattern_shouldThrowException() {
        String fileName = "mr_18f026ab-552a-4963-b058_20190521_001.csv";
        assertThatThrownBy(() -> fileNameParser.parse(fileName)).hasMessage("Invalid file name: " + fileName + ". A file name should match the following pattern: mr_[meter UUID]_[reading date yyyy-MM-dd]_[sequence number].csv.");
    }

    @Test
    void whenReadingDateIsInvalid_shouldThrowException() {
        String fileName = "mr_18f026ab-552a-4963-b058-57eae0c5ce59_2019-19-05_001.csv";
        assertThatThrownBy(() -> fileNameParser.parse(fileName)).hasMessage("Invalid reading date in the file name: 2019-19-05. A date should match the following pattern: yyyy-MM-dd.");
    }
}