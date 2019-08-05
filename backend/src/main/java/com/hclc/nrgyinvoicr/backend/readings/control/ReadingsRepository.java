package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import com.hclc.nrgyinvoicr.backend.readings.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ReadingsRepository extends JpaRepository<Reading, Long> {

    List<Reading> findByReadingSpreadSinceClosedLessThanAndReadingSpreadUntilOpenGreaterThanAndMeter(ZonedDateTime untilOpen, ZonedDateTime sinceClosed, Meter meter);
}
