package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ReadingValuesRepositoryCustomImpl implements ReadingValuesRepositoryCustom {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public void persist(ReadingValue readingValue) {
        em.persist(readingValue);
    }
}
