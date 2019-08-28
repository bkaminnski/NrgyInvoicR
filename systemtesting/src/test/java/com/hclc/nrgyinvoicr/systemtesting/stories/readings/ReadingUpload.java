package com.hclc.nrgyinvoicr.systemtesting.stories.readings;

public class ReadingUpload {
    public final String fileName;
    public final String filePathWithName;
    public final String meterSerialNumber;
    public final int numberOfValues;

    ReadingUpload(String fileName, String filePathWithName, String meterSerialNumber, int numberOfValues) {
        this.fileName = fileName;
        this.filePathWithName = filePathWithName;
        this.meterSerialNumber = meterSerialNumber;
        this.numberOfValues = numberOfValues;
    }
}
