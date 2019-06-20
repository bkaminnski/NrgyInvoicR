package com.hclc.nrgyinvoicr.backend.clients.control;

public class MeterAlreadyAssignedToClientException extends Exception {

    MeterAlreadyAssignedToClientException(String meterSerialNumber, String clientNumberAlreadyAssigned, String clientNumberToReassignTo) {
        super("A meter " + meterSerialNumber + " is already assigned to client " + clientNumberAlreadyAssigned + " and can not be reassigned to client " + clientNumberToReassignTo);
    }
}
