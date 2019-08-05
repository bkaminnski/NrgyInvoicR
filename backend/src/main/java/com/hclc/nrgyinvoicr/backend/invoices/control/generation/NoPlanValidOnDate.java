package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;

import java.time.ZonedDateTime;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;

class NoPlanValidOnDate extends Exception {
    NoPlanValidOnDate(Client client, ZonedDateTime validOnDate) {
        super("Client " + client.getNumber() + ": No plan found on date " + validOnDate.format(ofPattern(ISO_8601_DATE)));
    }
}

