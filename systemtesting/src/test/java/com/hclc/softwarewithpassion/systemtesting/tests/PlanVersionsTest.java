package com.hclc.softwarewithpassion.systemtesting.tests;

import com.hclc.softwarewithpassion.systemtesting.SystemTest;
import com.hclc.softwarewithpassion.systemtesting.stories.plans.PlanVersion;
import com.hclc.softwarewithpassion.systemtesting.stories.plans.PlanVersionDefinitionStory;
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
