package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.control.files.ReadingException;
import com.hclc.nrgyinvoicr.backend.readings.control.files.ReadingsFilesService;
import com.hclc.nrgyinvoicr.backend.readings.entity.Reading;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingsUploadsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZonedDateTime;

import static com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUploadsSpecifications.includeErrors;
import static com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUploadsSpecifications.uploadDateBetween;

@Service
public class ReadingsUploadsService {
    private final ReadingsUploadsRepository readingsUploadsRepository;
    private final ReadingsFilesService readingsFilesService;

    ReadingsUploadsService(ReadingsUploadsRepository readingsUploadsRepository, ReadingsFilesService readingsFilesService) {
        this.readingsUploadsRepository = readingsUploadsRepository;
        this.readingsFilesService = readingsFilesService;
    }

    public ReadingUpload saveReadingUpload(String fileName, InputStream fileContent) throws ReadingException, IOException {
        try {
            Reading reading = readingsFilesService.saveReading(fileName, fileContent);
            ReadingUpload readingUpload = readingsUploadsRepository.saveAndFlush(new ReadingUpload(fileName, reading));
            return readingUpload;
        } catch (ReadingException e) {
            readingsUploadsRepository.saveAndFlush(new ReadingUpload(fileName, e.getMessage()));
            throw e;
        }
    }

    public Page<ReadingUpload> findReadingsUploads(ReadingsUploadsSearchCriteria criteria) {
        ZonedDateTime since = criteria.getDateSince();
        ZonedDateTime until = criteria.getDateUntil();
        boolean includeErrors = criteria.isIncludeErrors();
        Specification<ReadingUpload> specification = uploadDateBetween(since, until).and(includeErrors(includeErrors));
        return readingsUploadsRepository.findAll(specification, criteria.getPageDefinition().asPageRequest());
    }
}
