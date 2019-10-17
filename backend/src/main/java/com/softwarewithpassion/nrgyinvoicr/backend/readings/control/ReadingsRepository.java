package com.softwarewithpassion.nrgyinvoicr.backend.readings.control;

import com.softwarewithpassion.nrgyinvoicr.backend.meters.entity.Meter;
import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.Reading;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ReadingsRepository extends JpaRepository<Reading, Long> {

    List<Reading> findByReadingSpreadSinceClosedLessThanAndReadingSpreadUntilOpenGreaterThanAndMeter(ZonedDateTime untilOpen, ZonedDateTime sinceClosed, Meter meter);
}
