package com.hclc.nrgyinvoicr.backend.clients.control;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;
import com.hclc.nrgyinvoicr.backend.plans.entity.Plan;

import java.time.ZonedDateTime;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;

public class NoPlanVersionValidOnDate extends Exception {
    NoPlanVersionValidOnDate(Client client, Plan plan, ZonedDateTime onDate) {
        super("No version of a plan \"" + plan.getName() + "\" found for client " + client.getNumber() + " on date " + onDate.format(ofPattern(ISO_8601_DATE)));
    }
}

