package com.hclc.nrgyinvoicr.backend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
@ConfigurationProperties(prefix = "nrgyinvoicr")
public class NrgyInvoicRConfig {
    private String timeZone;

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public ZoneId getTimeZoneAsZoneId() {
        return ZoneId.of(timeZone);
    }
}
