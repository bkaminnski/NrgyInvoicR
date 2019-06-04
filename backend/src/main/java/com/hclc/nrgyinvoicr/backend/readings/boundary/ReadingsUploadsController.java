package com.hclc.nrgyinvoicr.backend.readings.boundary;

import com.hclc.nrgyinvoicr.backend.ErrorResponse;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingsUploadsRepository;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingsUploadsService;
import com.hclc.nrgyinvoicr.backend.readings.control.files.ReadingException;
import com.hclc.nrgyinvoicr.backend.readings.control.files.ReadingsFilesService;
import com.hclc.nrgyinvoicr.backend.readings.entity.Reading;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingsUploadsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/readingsUploads")
public class ReadingsUploadsController {
    private final ReadingsUploadsService readingsUploadsService;
    private final ReadingsFilesService readingsFilesService;
    private final ReadingsUploadsRepository readingsUploadsRepository;

    public ReadingsUploadsController(ReadingsUploadsService readingsUploadsService, ReadingsFilesService readingsFilesService, ReadingsUploadsRepository readingsUploadsRepository) {
        this.readingsUploadsService = readingsUploadsService;
        this.readingsFilesService = readingsFilesService;
        this.readingsUploadsRepository = readingsUploadsRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<ReadingUpload> findReadingsUploads(ReadingsUploadsSearchCriteria criteria) {
        return readingsUploadsService.findReadingsUploads(criteria);
    }

    @PostMapping
    @Transactional(rollbackFor = {IOException.class})
    public ResponseEntity<ReadingUpload> handleReadingUpload(@RequestParam("file") MultipartFile file) throws ReadingException, IOException {
        String fileName = file.getOriginalFilename();
        InputStream fileContent = file.getInputStream();
        try {
            Reading reading = readingsFilesService.saveReading(fileName, fileContent);
            ReadingUpload readingUpload = readingsUploadsRepository.saveAndFlush(new ReadingUpload(fileName, reading));
            return ResponseEntity.ok(readingUpload);
        } catch (ReadingException e) {
            readingsUploadsRepository.saveAndFlush(new ReadingUpload(fileName, e.getMessage()));
            throw e;
        }
    }

    @ExceptionHandler(ReadingException.class)
    public ResponseEntity<ErrorResponse> handleReadingException(ReadingException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
