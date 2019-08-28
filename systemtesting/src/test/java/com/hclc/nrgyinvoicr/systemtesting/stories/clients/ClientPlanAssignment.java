package com.hclc.nrgyinvoicr.systemtesting.stories.clients;

public class ClientPlanAssignment {
    final String validSince;
    final String validSinceShortYear;
    final String planName;

    ClientPlanAssignment(String validSince, String validSinceShortYear, String planName) {
        this.validSince = validSince;
        this.validSinceShortYear = validSinceShortYear;
        this.planName = planName;
    }
}
