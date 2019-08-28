package com.hclc.nrgyinvoicr.systemtesting.stories.clients;

public class ClientPlanAssignment {
    public final String validSince;
    public final String validSinceShortYear;
    public final String planName;

    ClientPlanAssignment(String validSince, String validSinceShortYear, String planName) {
        this.validSince = validSince;
        this.validSinceShortYear = validSinceShortYear;
        this.planName = planName;
    }
}
