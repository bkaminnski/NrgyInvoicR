package com.softwarewithpassion.nrgyinvoicr.backend.readings.control;

import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.ReadingValue;

public interface ReadingValuesRepositoryCustom {
    void persist(ReadingValue readingValue);
}
