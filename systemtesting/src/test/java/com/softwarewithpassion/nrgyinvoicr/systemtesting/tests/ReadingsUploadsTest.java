package com.softwarewithpassion.nrgyinvoicr.systemtesting.tests;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.SystemTest;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters.MeterRegistrationStory;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.readings.ReadingUpload;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.readings.ReadingUploadStory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ReadingsUploadsTest extends SystemTest {
    private Meter meter;

    @BeforeEach
    void beforeEach() {
        this.meter = new MeterRegistrationStory(app).userRegistersANewMeter();
    }

    @Test
    void userUploadsAReadingFile() throws IOException {
        ReadingUploadStory readingsUploadStory = new ReadingUploadStory(app);

        ReadingUpload readingUpload = readingsUploadStory.userUploadsAReadingFile(meter);

        readingsUploadStory.assertThatUserSeesUploadedReadingFileInUploadProgressTable(readingUpload);
        readingsUploadStory.assertThatUserSeesUploadedReadingFileInReadingsUploadsHistory(readingUpload);
    }
}
