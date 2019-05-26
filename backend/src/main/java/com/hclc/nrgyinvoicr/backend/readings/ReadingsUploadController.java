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

    public ReadingsUploadController(FileNameParser fileNameParser, MetersRepository metersRepository, ImportsRepository importsRepository, ReadingLineParser readingLineParser, ReadingsRepository readingsRepository) {
        this.fileNameParser = fileNameParser;
        this.metersRepository = metersRepository;
        this.importsRepository = importsRepository;
        this.readingLineParser = readingLineParser;
        this.readingsRepository = readingsRepository;
    }

    @PostMapping(value = "/api/readings")
    @Transactional
    public ResponseEntity<Void> handleUploadedReadingFile(@RequestParam("file") MultipartFile file) throws IOException, FileNameParsingException, ReadingUploadException, ReadingLineParserException {
        String fileName = file.getOriginalFilename();
        ParsedFileName parsedFileName = fileNameParser.parse(fileName);
        Meter meter = metersRepository
                .findByExternalId(parsedFileName.getMeterId())
                .orElseThrow(() -> new ReadingUploadException("Meter not found: " + parsedFileName.getMeterId()));
        Import anImport = importsRepository.saveAndFlush(new Import(fileName, parsedFileName.getReadingDate(), meter));
        Date readingSinceClosed = null;
        Date readingUntilOpen = null;
        int lineNumber = 0;
        Reading reading = null;
        Long importId = anImport.getId();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                reading = readingLineParser.parse(importId, lineNumber, line);
                readingsRepository.save(reading);
                if (readingSinceClosed == null) {
                    readingSinceClosed = reading.getDate();
                }
            }
        }
        if (reading != null) {
            readingUntilOpen = Date.from(reading.getDate().toInstant().plus(15, ChronoUnit.MINUTES));
        }
        if (lineNumber == 0) {
            throw new ReadingUploadException("File is empty: " + fileName);
        }
        anImport.updateWithReadingsPeriod(readingSinceClosed, readingUntilOpen, lineNumber);
        importsRepository.save(anImport);
        return new ResponseEntity<>(OK);
    }

    @ExceptionHandler({FileNameParsingException.class, ReadingLineParserException.class, ReadingUploadException.class})
    public ResponseEntity<ErrorResponse> handleSpecificException(Exception e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
