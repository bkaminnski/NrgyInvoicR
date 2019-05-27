package com.hclc.nrgyinvoicr.backend.readings.files;

import com.hclc.nrgyinvoicr.backend.meters.Meter;
import com.hclc.nrgyinvoicr.backend.meters.MetersRepository;
import com.hclc.nrgyinvoicr.backend.readings.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class ReadingFilesService {
    private final FileNameParser fileNameParser;
    private final MetersRepository metersRepository;
    private final ReadingLineParser readingLineParser;
    private final ReadingsRepository readingsRepository;
    private final ReadingValuesRepository readingValuesRepository;

    public ReadingFilesService(FileNameParser fileNameParser, MetersRepository metersRepository, ReadingLineParser readingLineParser, ReadingsRepository readingsRepository, ReadingValuesRepository readingValuesRepository) {
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
        ReadingSpread readingSpread = saveReadingValues(fileContent, reading.getId());
        updateReadingWithReadingSpread(reading, readingSpread);
        return reading;
    }

    private Reading saveReading(Date readingDate, Meter meter) {
        return readingsRepository.saveAndFlush(new Reading(readingDate, meter));
    }

    private ParsedFileName parseFileName(String fileName) throws ReadingException {
        return fileNameParser.parse(fileName);
    }

    private Meter findMeter(String meterId) throws ReadingException {
        return metersRepository
                .findByExternalId(meterId)
                .orElseThrow(() -> new ReadingException("Meter not found: " + meterId));
    }

    private ReadingSpread saveReadingValues(InputStream fileContent, Long readingId) throws IOException, ReadingException {
        try (ReadingValuesReader readingValuesReader = new ReadingValuesReader(fileContent, readingLineParser, readingId)) {
            ReadingValue readingValue;
            while ((readingValue = readingValuesReader.readReadingValue()) != null) {
                readingValuesRepository.save(readingValue);
            }
            return readingValuesReader.determineReadingsSpread();
        }
    }

    private void updateReadingWithReadingSpread(Reading reading, ReadingSpread readingSpread) {
        readingsRepository.save(reading.updatedWithReadingsSpread(readingSpread));
    }
}
