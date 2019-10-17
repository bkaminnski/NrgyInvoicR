package com.softwarewithpassion.nrgyinvoicr.backend.clients.control;

import com.softwarewithpassion.nrgyinvoicr.backend.clients.entity.Client;

import java.time.ZonedDateTime;

import static com.softwarewithpassion.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;
import static java.time.format.DateTimeFormatter.ofPattern;

public class NoPlanValidOnDate extends Exception {
    NoPlanValidOnDate(Client client, ZonedDateTime validOnDate) {
        super("No plan found for client " + client.getNumber() + " on date " + validOnDate.format(ofPattern(ISO_8601_DATE)));
    }
}

