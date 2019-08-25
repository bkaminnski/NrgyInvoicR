package com.hclc.nrgyinvoicr.systemtesting.tests;

import com.hclc.nrgyinvoicr.systemtesting.SystemTest;
import com.hclc.nrgyinvoicr.systemtesting.stories.plans.PlanVersion;
import com.hclc.nrgyinvoicr.systemtesting.stories.plans.PlanVersionDefinitionStory;
import org.junit.jupiter.api.Test;

class PlanVersionsTest extends SystemTest {

    @Test
    void userDefinesANewVersionOfAWeekendPlan() {
        PlanVersionDefinitionStory planVersionDefinitionStory = new PlanVersionDefinitionStory(app);

        PlanVersion planVersion = planVersionDefinitionStory.userDefinesANewVersionOfAPlan("Weekend Plan");

        planVersionDefinitionStory.assertThatUserSeesADefinedPlanVersionInAListOfPlanVersions(planVersion);
        planVersionDefinitionStory.assertThatPlanVersionDefinitionFormContainsAllFieldsForA(planVersion);
    }
}
