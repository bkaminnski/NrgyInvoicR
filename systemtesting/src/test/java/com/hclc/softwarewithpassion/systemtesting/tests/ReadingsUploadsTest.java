package com.hclc.softwarewithpassion.systemtesting.tests;

import com.hclc.softwarewithpassion.systemtesting.SystemTest;
import com.hclc.softwarewithpassion.systemtesting.stories.meters.Meter;
import com.hclc.softwarewithpassion.systemtesting.stories.meters.MeterRegistrationStory;
import com.hclc.softwarewithpassion.systemtesting.stories.readings.ReadingUpload;
import com.hclc.softwarewithpassion.systemtesting.stories.readings.ReadingUploadStory;
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
