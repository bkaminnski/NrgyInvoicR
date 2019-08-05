package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.plans.entity.Plan;

import java.time.ZonedDateTime;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;

class NoPlanVersionValidOnDate extends Exception {
    NoPlanVersionValidOnDate(Client client, Plan plan, ZonedDateTime onDate) {
        super("Client " + client.getNumber() + ": No version of a plan \"" + plan.getName() + "\" found on date " + onDate.format(ofPattern(ISO_8601_DATE)));
    }
}

