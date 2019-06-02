package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingsUploadsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUploadsSpecifications.includeErrors;
import static com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUploadsSpecifications.uploadDateBetween;

@Service
public class ReadingsUploadsService {
    private final ReadingsUploadsRepository readingUploadsRepository;

    ReadingsUploadsService(ReadingsUploadsRepository readingUploadsRepository) {
        this.readingUploadsRepository = readingUploadsRepository;
    }

    public Page<ReadingUpload> findReadingsUploads(ReadingsUploadsSearchCriteria criteria) {
        ZonedDateTime since = criteria.getDateSince();
        ZonedDateTime until = criteria.getDateUntil();
        boolean includeErrors = criteria.isIncludeErrors();
        Specification<ReadingUpload> specification = uploadDateBetween(since, until).and(includeErrors(includeErrors));
        return readingUploadsRepository.findAll(specification, criteria.getPageDefinition().asPageRequest());
    }
}
