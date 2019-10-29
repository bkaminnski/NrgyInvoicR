package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.plans;

public class PlanVersionBuilder {
    private String validSince;
    private String validSinceShortYear;
    private String subscriptionFee;
    private String networkFee;
    private String description;
    private String expression;
    private String weekPrice;
    private String weekendPrice;

    public static PlanVersionBuilder aPlanVersion() {
        return new PlanVersionBuilder();
    }

    public PlanVersionBuilder withValidSince(String validSince) {
        this.validSince = validSince;
        return this;
    }

    public PlanVersionBuilder withValidSinceShortYear(String validSinceShortYear) {
        this.validSinceShortYear = validSinceShortYear;
        return this;
    }

    public PlanVersionBuilder withSubscriptionFee(String subscriptionFee) {
        this.subscriptionFee = subscriptionFee;
        return this;
    }

    public PlanVersionBuilder withNetworkFee(String networkFee) {
        this.networkFee = networkFee;
        return this;
    }

    public PlanVersionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PlanVersionBuilder withExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public PlanVersionBuilder withWeekPrice(String weekPrice) {
        this.weekPrice = weekPrice;
        return this;
    }

    public PlanVersionBuilder withWeekendPrice(String weekendPrice) {
        this.weekendPrice = weekendPrice;
        return this;
    }

    public PlanVersion build() {
        return new PlanVersion(validSince, validSinceShortYear, subscriptionFee, networkFee, description, expression, weekPrice, weekendPrice);
    }
}