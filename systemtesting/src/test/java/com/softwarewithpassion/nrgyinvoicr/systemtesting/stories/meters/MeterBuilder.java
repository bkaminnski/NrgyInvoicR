package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.meters;

import static java.util.UUID.randomUUID;

public class MeterBuilder {
    private String serialNumber = randomUUID().toString();

    public static MeterBuilder aMeter() {
        return new MeterBuilder();
    }

    public MeterBuilder withSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public Meter build() {
        return new Meter(serialNumber);
    }
}