package com.softwarewithpassion.nrgyinvoicr.backend.clients.control;

import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.Client;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.Plan;

import java.time.ZonedDateTime;

import static com.softwarewithpassion.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;

public class NoPlanVersionValidOnDate extends Exception {
    NoPlanVersionValidOnDate(Client client, Plan plan, ZonedDateTime onDate) {
        super("No version of a plan \"" + plan.getName() + "\" found for client " + client.getNumber() + " on date " + onDate.format(ofPattern(ISO_8601_DATE)));
    }
}

