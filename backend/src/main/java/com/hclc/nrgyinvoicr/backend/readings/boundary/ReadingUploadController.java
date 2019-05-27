package com.hclc.nrgyinvoicr.backend.readings.boundary;

import com.hclc.nrgyinvoicr.backend.ErrorResponse;
import com.hclc.nrgyinvoicr.backend.readings.control.ReadingUploadsRepository;
import com.hclc.nrgyinvoicr.backend.readings.control.files.ReadingException;
import com.hclc.nrgyinvoicr.backend.readings.control.files.ReadingFilesService;
import com.hclc.nrgyinvoicr.backend.readings.entity.Reading;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Controller
public class ReadingUploadController {
    private final ReadingFilesService readingFilesService;
    private final ReadingUploadsRepository readingUploadsRepository;

    public ReadingUploadController(ReadingFilesService readingFilesService, ReadingUploadsRepository readingUploadsRepository) {
        this.readingFilesService = readingFilesService;
        this.readingUploadsRepository = readingUploadsRepository;
    }

    @PostMapping(value = "/api/readingUploads")
    @Transactional(rollbackFor = {ReadingException.class, IOException.class})
    public ResponseEntity<Void> handleReadingUpload(@RequestParam("file") MultipartFile file) throws ReadingException, IOException {
        String fileName = file.getOriginalFilename();
        InputStream fileContent = file.getInputStream();
        Reading reading = readingFilesService.saveReading(fileName, fileContent);
        readingUploadsRepository.saveAndFlush(new ReadingUpload(fileName, reading));
        return new ResponseEntity<>(OK);
    }

    @ExceptionHandler(ReadingException.class)
    public ResponseEntity<ErrorResponse> handleReadingException(ReadingException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
