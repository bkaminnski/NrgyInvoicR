package com.hclc.nrgyinvoicr.backend.readings;

import org.springframework.data.repository.CrudRepository;

interface ReadingsRepository extends CrudRepository<Reading, ReadingId> {
}
