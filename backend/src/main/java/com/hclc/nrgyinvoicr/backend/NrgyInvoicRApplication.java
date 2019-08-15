package com.hclc.nrgyinvoicr.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.time.Clock;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class NrgyInvoicRApplication {

    public static void main(String[] args) {
        SpringApplication.run(NrgyInvoicRApplication.class, args);
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
