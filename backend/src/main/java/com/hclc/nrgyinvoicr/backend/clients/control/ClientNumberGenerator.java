package com.hclc.nrgyinvoicr.backend.clients.control;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class ClientNumberGenerator {

    String generateClientNumber() {
        return UUID.randomUUID().toString();
    }
}
