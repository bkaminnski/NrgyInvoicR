package com.hclc.nrgyinvoicr.backend;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, Long id) {
        super("Entity " + clazz + " with id " + id + " was not found.");
    }
}
