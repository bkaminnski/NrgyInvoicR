package com.hclc.nrgyinvoicr.systemtesting.tests;

import com.hclc.nrgyinvoicr.systemtesting.SystemTest;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.MeterRegistrationStory;
import org.junit.jupiter.api.Test;

class MeterRegistrationTest extends SystemTest {

    @Test
    void userRegistersAMeter() {
        MeterRegistrationStory meterRegistrationStory = new MeterRegistrationStory(app);

        Meter meter = meterRegistrationStory.userRegistersANewMeter();

        meterRegistrationStory.assertThatUserSeesARegisteredMeterInAListOfMeters(meter);
    }
}
