package com.hclc.nrgyinvoicr.backend.readings.control.files;

import com.hclc.nrgyinvoicr.backend.meters.control.MetersRepository;
import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingValuesRepository;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingsRepository;
import com.hclc.nrgyinvoicr.backend.readings.entity.Reading;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingSpread;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingValue;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;

@Service
public class ReadingsFilesService {
    private final FileNameParser fileNameParser;
    private final MetersRepository metersRepository;
    private final ReadingLineParser readingLineParser;
    private final ReadingsRepository readingsRepository;
    private final ReadingValuesRepository readingValuesRepository;

    public ReadingsFilesService(FileNameParser fileNameParser, MetersRepository metersRepository, ReadingLineParser readingLineParser, ReadingsRepository readingsRepository, ReadingValuesRepository readingValuesRepository) {
        this.fileNameParser = fileNameParser;
        this.metersRepository = metersRepository;
        this.readingLineParser = readingLineParser;
        this.readingsRepository = readingsRepository;
        this.readingValuesRepository = readingValuesRepository;
    }

    public Reading saveReading(String fileName, InputStream fileContent) throws ReadingException, IOException {
        ParsedFileName parsedFileName = parseFileName(fileName);
        Meter meter = findMeter(parsedFileName.getMeterId());
        Reading reading = saveReading(parsedFileName.getReadingDate(), meter);
        try {
            ReadingSpread readingSpread = saveReadingValues(fileContent, reading.getId());
            updateReadingWithReadingSpread(reading, readingSpread);
        } catch (ReadingException e) {
            rollbackReadingCreation(reading);
            throw e;
        }
        return reading;
    }

    private Reading saveReading(LocalDate readingDate, Meter meter) {
        return readingsRepository.saveAndFlush(new Reading(readingDate, meter));
    }

    private ParsedFileName parseFileName(String fileName) throws ReadingException {
        return fileNameParser.parse(fileName);
    }

    private Meter findMeter(String serialNumber) throws MeterNotFoundException {
        return metersRepository
                .findBySerialNumber(serialNumber)
                .orElseThrow(() -> new MeterNotFoundException(serialNumber));
    }

    private ReadingSpread saveReadingValues(InputStream fileContent, Long readingId) throws IOException, ReadingException {
        try (ReadingValuesReader readingValuesReader = new ReadingValuesReader(fileContent, readingLineParser, readingId)) {
            ReadingValue readingValue;
            while ((readingValue = readingValuesReader.readReadingValue()) != null) {
                readingValuesRepository.save(readingValue);
            }
            return readingValuesReader.determineReadingSpread();
        }
    }

    private void updateReadingWithReadingSpread(Reading reading, ReadingSpread readingSpread) {
        readingsRepository.save(reading.updatedWithReadingsSpread(readingSpread));
    }

    private void rollbackReadingCreation(Reading reading) {
        readingValuesRepository.deleteByReadingId(reading.getId());
        readingsRepository.deleteById(reading.getId());
    }
}
