package com.hclc.nrgyinvoicr.systemtesting.stories.readings;

public class ReadingUploadBuilder {
    private String fileName;
    private String filePathWithName;
    private String meterSerialNumber;
    private int numberOfValues;

    public static ReadingUploadBuilder aReadingUpload() {
        return new ReadingUploadBuilder();
    }

    public ReadingUploadBuilder withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public ReadingUploadBuilder withFilePathAndName(String filePathWithName) {
        this.filePathWithName = filePathWithName;
        return this;
    }

    public ReadingUploadBuilder withMeterSerialNumber(String meterSerialNumber) {
        this.meterSerialNumber = meterSerialNumber;
        return this;
    }

    public ReadingUploadBuilder withNumberOfValues(int numberOfValues) {
        this.numberOfValues = numberOfValues;
        return this;
    }

    public ReadingUpload build() {
        return new ReadingUpload(fileName, filePathWithName, meterSerialNumber, numberOfValues);
    }
}