package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.readings;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.Application;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.readings.ReadingUploadBuilder.aReadingUpload;
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
        app.clickWith30sTimeout(By.id("ae-button-meter-readings"));
        app.clickWith30sTimeout(By.id("ae-button-new-upload"));
    }

    private ReadingUpload prepareFileToUpload(Meter meter) throws IOException {
        String fileName = "mr_" + meter.serialNumber + "_2019-08-05_001.csv";
        ReadingUpload readingUpload = aReadingUpload()
                .withFileName(fileName)
                .withFilePathAndName(Paths.get(System.getProperty("java.io.tmpdir"), fileName).toString())
                .withMeterSerialNumber(meter.serialNumber)
                .withNumberOfValues(2976)
                .withWeekQuantity("182.256")
                .withWeekendQuantity("72.7448")
                .build();

        InputStream inputStream = this.getClass().getResourceAsStream("/readings_01_2019-07.csv");
        Files.copy(inputStream, Paths.get(readingUpload.filePathWithName), REPLACE_EXISTING);
        return readingUpload;
    }

    private void startUpload(ReadingUpload readingUpload) {
        app.findTimeoutableElementWith30sTimeout(By.id("ae-input-file-upload")).sendKeys(readingUpload.filePathWithName);
    }

    private void waitUntilUploadCompletes(ReadingUpload readingUpload) {
        app.waitUpTo30sUntilElementIsVisible(By.xpath("//*[@id='ae-table-upload-progress']/mat-row/mat-cell[text()=' " + readingUpload.fileName + " ']/..//mat-icon[text() = ' cloud_done ']"));
    }

    public void assertThatUserSeesUploadedReadingFileInUploadProgressTable(ReadingUpload readingUpload) {
        assertThatCode(() -> {
            WebElement uploadProgressRow = app.findTimeoutableElementWith30sTimeout(By.xpath("//*[@id='ae-table-upload-progress']/mat-row/mat-cell[text()=' " + readingUpload.fileName + " ']/.."));
            uploadProgressRow.findElement(By.xpath("mat-cell[@id='ae-cell-upload-progress-measured-values' and text()=' " + readingUpload.numberOfValues + " ']"));
            uploadProgressRow.findElement(By.xpath("mat-cell[@id='ae-cell-upload-progress-expected-values' and text()=' " + readingUpload.numberOfValues + " ']"));
        }).doesNotThrowAnyException();
    }

    public void assertThatUserSeesUploadedReadingFileInReadingsUploadsHistory(ReadingUpload readingUpload) {
        navigateToReadingsUploadsHistoryPage(readingUpload);
        assertThatCode(() -> {
            WebElement readingUploadRow = app.findTimeoutableElementWith30sTimeout(By.xpath("//*[@id='ae-table-readings-uploads']/mat-row/mat-cell[text() = '" + readingUpload.fileName + "']/.."));
            readingUploadRow.findElement(By.xpath("mat-cell[@id='ae-cell-readings-uploads-measured-values' and text()=' " + readingUpload.numberOfValues + " ']"));
            readingUploadRow.findElement(By.xpath("mat-cell[@id='ae-cell-readings-uploads-expected-values' and text()=' " + readingUpload.numberOfValues + " ']"));
            readingUploadRow.findElement(By.xpath("//mat-icon[text() = ' cloud_done ']"));
        }).doesNotThrowAnyException();
    }

    private void navigateToReadingsUploadsHistoryPage(ReadingUpload readingUpload) {
        app.clickWith30sTimeout(By.id("ae-button-meter-readings"));
        app.clickWith30sTimeout(By.id("ae-button-history-of-uploads"));
    }
}
