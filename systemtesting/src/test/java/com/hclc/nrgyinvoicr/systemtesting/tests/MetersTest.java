package com.hclc.nrgyinvoicr.systemtesting.tests;

import com.hclc.nrgyinvoicr.systemtesting.SystemTest;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.hclc.nrgyinvoicr.systemtesting.stories.meters.MeterRegistrationStory;
import org.junit.jupiter.api.Test;

class MetersTest extends SystemTest {

    @Test
    void userRegistersANewMeter() {
        MeterRegistrationStory meterRegistrationStory = new MeterRegistrationStory(app);

        Meter meter = meterRegistrationStory.userRegistersANewMeter();

        meterRegistrationStory.assertThatUserSeesARegisteredMeterInAListOfMeters(meter);
        meterRegistrationStory.assertThatMeterRegistrationFormContainsAllFieldsForA(meter);
    }
}
