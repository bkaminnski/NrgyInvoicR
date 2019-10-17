package com.softwarewithpassion.nrgyinvoicr.backend.plans.control.expression.buckets;

public abstract class LineException extends RuntimeException {

    public abstract LineError toLineError();
}
