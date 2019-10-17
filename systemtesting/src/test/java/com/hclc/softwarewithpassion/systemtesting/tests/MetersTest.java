package com.hclc.softwarewithpassion.systemtesting.tests;

import com.hclc.softwarewithpassion.systemtesting.SystemTest;
import com.hclc.softwarewithpassion.systemtesting.stories.meters.Meter;
import com.hclc.softwarewithpassion.systemtesting.stories.meters.MeterRegistrationStory;
import org.junit.jupiter.api.Test;

class MetersTest extends SystemTest {

    @Test
    void userRegistersANewMeter() {
        MeterRegistrationStory meterRegistrationStory = new MeterRegistrationStory(app);

        Meter meter = meterRegistrationStory.userRegistersANewMeter();

        meterRegistrationStory.assertThatUserSeesARegisteredMeterInAListOfMeters(meter);
        meterRegistrationStory.assertThatMeterRegistrationFormShowsAllValuesForA(meter);
    }
}
