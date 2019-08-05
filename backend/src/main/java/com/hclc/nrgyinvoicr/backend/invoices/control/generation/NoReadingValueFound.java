package com.hclc.nrgyinvoicr.backend.invoices.control.generation;

import com.hclc.nrgyinvoicr.backend.clients.entity.Client;

class NoReadingValueFound extends Exception {
    NoReadingValueFound(Client client) {
        super("Client " + client.getNumber() + ": No reading value was found");
    }
}

