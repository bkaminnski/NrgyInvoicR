package com.softwarewithpassion.nrgyinvoicr.backend.meters.control;

public class MeterAlreadyRegisteredException extends Exception {

    MeterAlreadyRegisteredException(String serialNumber) {
        super("A meter with serial number " + serialNumber + " has been already registered.");
    }
}
