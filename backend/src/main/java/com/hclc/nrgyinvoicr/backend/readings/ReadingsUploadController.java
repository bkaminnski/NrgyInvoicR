package com.hclc.nrgyinvoicr.backend.readings;

import com.hclc.nrgyinvoicr.backend.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
public class ReadingsUploadController {
    private final FileNameParser fileNameParser;
    private final MetersRepository metersRepository;
    private final ImportsRepository importsRepository;
    private final ReadingLineParser readingLineParser;
    private final ReadingsRepository readingsRepository;
    private final ReadingValuesRepository readingValuesRepository;

    public ReadingsUploadController(FileNameParser fileNameParser, MetersRepository metersRepository, ImportsRepository importsRepository, ReadingLineParser readingLineParser, ReadingsRepository readingsRepository, ReadingValuesRepository readingValuesRepository) {
        this.fileNameParser = fileNameParser;
        this.metersRepository = metersRepository;
        this.importsRepository = importsRepository;
        this.readingLineParser = readingLineParser;
        this.readingsRepository = readingsRepository;
        this.readingValuesRepository = readingValuesRepository;
    }

    @PostMapping(value = "/api/readings")
    @Transactional
    public ResponseEntity<Void> handleUploadedReadingFile(@RequestParam("file") MultipartFile file) throws IOException, FileNameParsingException, ReadingUploadException, ReadingLineParserException {
        String fileName = file.getOriginalFilename();
        ParsedFileName parsedFileName = fileNameParser.parse(fileName);
        Meter meter = metersRepository
                .findByExternalId(parsedFileName.getMeterId())
                .orElseThrow(() -> new ReadingUploadException("Meter not found: " + parsedFileName.getMeterId()));
        Reading reading = readingsRepository.saveAndFlush(new Reading(parsedFileName.getReadingDate(), meter));
        Date readingSinceClosed = null;
        Date readingUntilOpen = null;
        int lineNumber = 0;
        ReadingValue readingValue = null;
        Long readingId = reading.getId();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                readingValue = readingLineParser.parse(readingId, lineNumber, line);
                readingValuesRepository.save(readingValue);
                if (readingSinceClosed == null) {
                    readingSinceClosed = readingValue.getDate();
                }
            }
        }
        if (readingValue != null) {
            readingUntilOpen = Date.from(readingValue.getDate().toInstant().plus(15, ChronoUnit.MINUTES));
        }
        if (lineNumber == 0) {
            throw new ReadingUploadException("File is empty: " + fileName);
        }
        reading.updateWithReadingsSpread(readingSinceClosed, readingUntilOpen, lineNumber);
        readingsRepository.save(reading);
        importsRepository.saveAndFlush(new Import(fileName, reading));
        return new ResponseEntity<>(OK);
    }

    @ExceptionHandler({FileNameParsingException.class, ReadingLineParserException.class, ReadingUploadException.class})
    public ResponseEntity<ErrorResponse> handleSpecificException(Exception e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
