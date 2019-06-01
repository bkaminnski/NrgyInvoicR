package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUploadsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUploadsSpecifications.includeErrors;
import static com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUploadsSpecifications.uploadDateBetween;

@Service
public class ReadingUploadsService {
    private final ReadingUploadsRepository readingUploadsRepository;

    ReadingUploadsService(ReadingUploadsRepository readingUploadsRepository) {
        this.readingUploadsRepository = readingUploadsRepository;
    }

    public Page<ReadingUpload> findReadingUploads(ReadingUploadsSearchCriteria criteria) {
        ZonedDateTime since = criteria.getDateSince();
        ZonedDateTime until = criteria.getDateUntil();
        boolean includeErrors = criteria.isIncludeErrors();
        Specification<ReadingUpload> specification = uploadDateBetween(since, until).and(includeErrors(includeErrors));
        return readingUploadsRepository.findAll(specification, criteria.getPageDefinition().asPageRequest());
    }
}
