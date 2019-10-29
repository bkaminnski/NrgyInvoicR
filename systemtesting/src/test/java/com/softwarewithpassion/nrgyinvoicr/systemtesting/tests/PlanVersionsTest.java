package com.softwarewithpassion.nrgyinvoicr.systemtesting.tests;

import com.softwarewithpassion.nrgyinvoicr.systemtesting.SystemTest;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.plans.PlanVersion;
import com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.plans.PlanVersionDefinitionStory;
import org.junit.jupiter.api.Test;

class PlanVersionsTest extends SystemTest {

    @Test
    void userDefinesANewVersionOfAWeekendPlan() {
        PlanVersionDefinitionStory planVersionDefinitionStory = new PlanVersionDefinitionStory(app);

        PlanVersion planVersion = planVersionDefinitionStory.userDefinesANewVersionOfAPlan("Weekend Plan");

        planVersionDefinitionStory.assertThatUserSeesADefinedPlanVersionInAListOfPlanVersions(planVersion);
        planVersionDefinitionStory.assertThatPlanVersionDefinitionFormShowsAllValuesForA(planVersion);
    }
}
