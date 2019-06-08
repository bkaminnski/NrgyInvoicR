package com.hclc.nrgyinvoicr.backend.meters.control;

public class MeterAlreadyRegisteredException extends Exception {

    MeterAlreadyRegisteredException(String message) {
        super(message);
    }
}
