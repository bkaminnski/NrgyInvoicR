package com.softwarewithpassion.nrgyinvoicr.backend.readings.entity;

import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public class ReadingUploadsSpecifications {
    private static final String UPLOAD_DATE = "date";
    private static final String STATUS = "status";

    public static Specification<ReadingUpload> uploadDateBetween(ZonedDateTime since, ZonedDateTime until) {
        return (readingUpload, query, cb) -> cb.and(
                since == null ? cb.and() : cb.greaterThanOrEqualTo(readingUpload.get(UPLOAD_DATE), since),
                until == null ? cb.and() : cb.lessThan(readingUpload.get(UPLOAD_DATE), until)
        );
    }

    public static Specification<ReadingUpload> includeErrors(boolean includeErrors) {
        return (readingUpload, query, cb) -> includeErrors ? cb.and() : cb.equal(readingUpload.get(STATUS), ReadingUploadStatus.OK);
    }
}
