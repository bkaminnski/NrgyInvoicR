package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingsRepository extends JpaRepository<Reading, Long> {
}
