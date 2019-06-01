package com.hclc.nrgyinvoicr.backend.readings.boundary;

import com.hclc.nrgyinvoicr.backend.ErrorResponse;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingUploadsRepository;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingUploadsService;
import com.hclc.nrgyinvoicr.backend.readings.control.files.ReadingException;
import com.hclc.nrgyinvoicr.backend.readings.control.files.ReadingFilesService;
import com.hclc.nrgyinvoicr.backend.readings.entity.Reading;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUploadsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController("/api/readingUploads")
public class ReadingUploadsController {
    private final ReadingUploadsService readingUploadsService;
    private final ReadingFilesService readingFilesService;
    private final ReadingUploadsRepository readingUploadsRepository;

    public ReadingUploadsController(ReadingUploadsService readingUploadsService, ReadingFilesService readingFilesService, ReadingUploadsRepository readingUploadsRepository) {
        this.readingUploadsService = readingUploadsService;
        this.readingFilesService = readingFilesService;
        this.readingUploadsRepository = readingUploadsRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<ReadingUpload> findReadingUploads(ReadingUploadsSearchCriteria readingUploadsSearchCriteria) {
        return readingUploadsService.findReadingUploads(readingUploadsSearchCriteria);
    }

    @PostMapping
    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<Void> handleReadingUpload(@RequestParam("file") MultipartFile file) throws ReadingException, IOException {
        String fileName = file.getOriginalFilename();
        InputStream fileContent = file.getInputStream();
        try {
            Reading reading = readingFilesService.saveReading(fileName, fileContent);
            readingUploadsRepository.saveAndFlush(new ReadingUpload(fileName, reading));
        } catch (ReadingException e) {
            readingUploadsRepository.saveAndFlush(new ReadingUpload(fileName, e.getMessage()));
            throw e;
        }
        return new ResponseEntity<>(OK);
    }

    @ExceptionHandler(ReadingException.class)
    public ResponseEntity<ErrorResponse> handleReadingException(ReadingException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
