package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.clients;

public class ClientPlanAssignmentBuilder {
    private String validSince;
    private String validSinceShortYear;
    private String planName;

    static ClientPlanAssignmentBuilder aClientPlanAssignment() {
        return new ClientPlanAssignmentBuilder();
    }

    public ClientPlanAssignmentBuilder withValidSince(String validSince) {
        this.validSince = validSince;
        return this;
    }

    public ClientPlanAssignmentBuilder withValidSinceShortYear(String validSinceShortYear) {
        this.validSinceShortYear = validSinceShortYear;
        return this;
    }

    public ClientPlanAssignmentBuilder withPlanName(String planName) {
        this.planName = planName;
        return this;
    }

    public ClientPlanAssignment build() {
        return new ClientPlanAssignment(validSince, validSinceShortYear, planName);
    }
}