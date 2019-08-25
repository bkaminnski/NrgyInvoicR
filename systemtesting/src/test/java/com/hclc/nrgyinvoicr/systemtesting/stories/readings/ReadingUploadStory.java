package com.hclc.nrgyinvoicr.systemtesting.stories.readings;

import com.hclc.nrgyinvoicr.systemtesting.Application;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.hclc.nrgyinvoicr.systemtesting.stories.readings.ReadingUploadBuilder.aReadingUpload;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

public class ReadingUploadStory {
    private final Application app;

    public ReadingUploadStory(Application app) {
        this.app = app;
    }

    public ReadingUpload userUploadsAReadingFile(Meter meter) throws IOException {
        navigateToReadingsUploadPage();
        ReadingUpload readingUpload = prepareFileToUpload(meter);
        startUpload(readingUpload);
        waitUntilUploadCompletes(readingUpload);
        return readingUpload;
    }

    private void navigateToReadingsUploadPage() {
        app.findElement(By.id("ae-button-meter-readings")).click();
        app.clickWith1sTimeout(By.id("ae-button-new-upload"));
    }

    private ReadingUpload prepareFileToUpload(Meter meter) throws IOException {
        String fileName = "mr_" + meter.serialNumber + "_2019-08-05_001.csv";
        ReadingUpload readingUpload = aReadingUpload()
                .withFileName(fileName)
                .withFilePathAndName(Paths.get(System.getProperty("java.io.tmpdir"), fileName).toString())
                .withMeterSerialNumber(meter.serialNumber)
                .withNumberOfValues(2976)
                .build();
        InputStream inputStream = this.getClass().getResourceAsStream("/readings_01_2019-07.csv");
        Files.copy(inputStream, Paths.get(readingUpload.filePathWithName), REPLACE_EXISTING);
        return readingUpload;
    }

    private void startUpload(ReadingUpload readingUpload) {
        app.findElement(By.id("ae-input-file-upload")).sendKeys(readingUpload.filePathWithName);
    }

    private void waitUntilUploadCompletes(ReadingUpload readingUpload) {
        app.waitUpTo30sUntilElementIsVisible(By.xpath("//*[@id='ae-table-upload-progress']/mat-row/mat-cell[text() = ' " + readingUpload.fileName + " ']/..//mat-icon[text() = ' cloud_done ']"));
    }

    public void assertThatUserSeesUploadedReadingFileInUploadProgressTable(ReadingUpload readingUpload) {
        assertThatCode(() -> {
            WebElement clientRow = app.findElement(By.xpath("//*[@id='ae-table-upload-progress']/mat-row/mat-cell[text() = ' " + readingUpload.fileName + " ']/.."));
            clientRow.findElement(By.xpath("mat-cell[@id='ae-cell-measured-values' and text() = ' " + readingUpload.numberOfValues + " ']"));
            clientRow.findElement(By.xpath("mat-cell[@id='ae-cell-expected-values' and text() = ' " + readingUpload.numberOfValues + " ']"));
        }).doesNotThrowAnyException();
    }

    public void assertThatUserSeesUploadedReadingFileInReadingsUploadsHistory(ReadingUpload readingUpload) {
        navigateToReadingsUploadsHistoryPage(readingUpload);
        assertThatCode(() -> {
            WebElement clientRow = app.findElement(By.xpath("//*[@id='ae-table-readings-uploads']/mat-row/mat-cell[text() = '" + readingUpload.fileName + "']/.."));
            clientRow.findElement(By.xpath("mat-cell[@id='ae-cell-measured-values' and text() = ' " + readingUpload.numberOfValues + " ']"));
            clientRow.findElement(By.xpath("mat-cell[@id='ae-cell-expected-values' and text() = ' " + readingUpload.numberOfValues + " ']"));
            clientRow.findElement(By.xpath("//mat-icon[text() = ' cloud_done ']"));
        }).doesNotThrowAnyException();
    }

    private void navigateToReadingsUploadsHistoryPage(ReadingUpload readingUpload) {
        app.findElement(By.id("ae-button-meter-readings")).click();
        app.clickWith1sTimeout(By.id("ae-button-history-of-uploads"));
    }
}
