package com.hclc.nrgyinvoicr.systemtesting.tests;

import com.hclc.nrgyinvoicr.systemtesting.SystemTest;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.MeterRegistrationStory;
import com.hclc.nrgyinvoicr.systemtesting.stories.readings.ReadingUpload;
import com.hclc.nrgyinvoicr.systemtesting.stories.readings.ReadingUploadStory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ReadingUploadTest extends SystemTest {
    private Meter meter;

    @BeforeEach
    void beforeEach() {
        this.meter = new MeterRegistrationStory(app).userRegistersANewMeter();
    }

    @Test
    void userUploadsAReadingFile() throws IOException {
        ReadingUploadStory readingsUploadStory = new ReadingUploadStory(app);

        ReadingUpload readingUpload = readingsUploadStory.userUploadsReadingFile(meter);

        readingsUploadStory.assertThatUserSeesUploadedReadingFileInUploadProgressTable(readingUpload);
    }
}
