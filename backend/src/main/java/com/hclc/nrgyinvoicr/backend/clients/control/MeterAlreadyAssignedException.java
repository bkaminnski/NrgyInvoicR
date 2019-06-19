package com.hclc.nrgyinvoicr.backend.clients.control;

public class MeterAlreadyAssignedException extends Exception {
    public MeterAlreadyAssignedException(String message) {
        super(message);
    }
}
