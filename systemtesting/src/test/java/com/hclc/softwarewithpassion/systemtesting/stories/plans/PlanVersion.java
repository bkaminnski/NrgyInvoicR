package com.hclc.softwarewithpassion.systemtesting.stories.plans;

public class PlanVersion {
    public final String validSince;
    public final String validSinceShortYear;
    public final String subscriptionFee;
    public final String networkFee;
    public final String description;
    public final String expression;
    public final String weekPrice;
    public final String weekendPrice;

    PlanVersion(String validSince, String validSinceShortYear, String subscriptionFee, String networkFee, String description, String expression, String weekPrice, String weekendPrice) {
        this.validSince = validSince;
        this.validSinceShortYear = validSinceShortYear;
        this.subscriptionFee = subscriptionFee;
        this.networkFee = networkFee;
        this.description = description;
        this.expression = expression;
        this.weekPrice = weekPrice;
        this.weekendPrice = weekendPrice;
    }
}
