package com.softwarewithpassion.nrgyinvoicr.backend.readings.control;

import com.softwarewithpassion.nrgyinvoicr.backend.meters.entity.Meter;
import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.Reading;
import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ReadingValuesService {
    private final ReadingsRepository readingsRepository;
    private final ReadingValuesRepository readingValuesRepository;

    public ReadingValuesService(ReadingsRepository readingsRepository, ReadingValuesRepository readingValuesRepository) {
        this.readingsRepository = readingsRepository;
        this.readingValuesRepository = readingValuesRepository;
    }

    public List<ReadingValue> findReadingValues(ZonedDateTime sinceClosed, ZonedDateTime untilOpen, Meter meter) throws NoReadingValueFound {
        List<Long> readingsIds = readingsRepository
                .findByReadingSpreadSinceClosedLessThanAndReadingSpreadUntilOpenGreaterThanAndMeter(
                        untilOpen,
                        sinceClosed,
                        meter
                )
                .stream()
                .map(Reading::getId)
                .collect(toList());
        List<ReadingValue> readingValues = readingValuesRepository
                .findByReadingIdInAndDateGreaterThanEqualAndDateLessThanOrderByDateAscReadingIdAsc(
                        readingsIds,
                        sinceClosed,
                        untilOpen
                );
        if (readingValues.isEmpty()) {
            throw new NoReadingValueFound(sinceClosed, untilOpen, meter);
        }
        return readingValues;
    }
}
