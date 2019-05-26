package com.hclc.nrgyinvoicr.backend.readings;

import org.springframework.data.repository.CrudRepository;

interface ReadingValuesRepository extends CrudRepository<ReadingValue, ReadingValueId> {
}
