package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValueId;
import org.springframework.data.repository.CrudRepository;

public interface ReadingValuesRepository extends CrudRepository<ReadingValue, ReadingValueId> {
}
