package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;

public interface ReadingValuesRepositoryCustom {
    void persist(ReadingValue readingValue);
}
