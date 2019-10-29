package com.softwarewithpassion.nrgyinvoicr.systemtesting.tests;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.SystemTest;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters.Meter;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters.MeterRegistrationStory;
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
