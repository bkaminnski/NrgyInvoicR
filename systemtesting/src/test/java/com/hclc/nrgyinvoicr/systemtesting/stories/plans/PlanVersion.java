package com.hclc.nrgyinvoicr.systemtesting.stories.plans;

public class PlanVersion {
    public final String validSince;
    public final String validSinceShortYear;
    public final String subscriptionFee;
    public final String networkFee;
    public final String description;
    public final String expression;

    PlanVersion(String validSince, String validSinceShortYear, String subscriptionFee, String networkFee, String description, String expression) {
        this.validSince = validSince;
        this.validSinceShortYear = validSinceShortYear;
        this.subscriptionFee = subscriptionFee;
        this.networkFee = networkFee;
        this.description = description;
        this.expression = expression;
    }
}
