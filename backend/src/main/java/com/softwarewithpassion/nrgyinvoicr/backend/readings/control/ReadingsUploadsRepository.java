package com.softwarewithpassion.nrgyinvoicr.backend.readings.control;

import com.softwarewithpassion.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

interface ReadingsUploadsRepository extends JpaRepository<ReadingUpload, Long>, JpaSpecificationExecutor<ReadingUpload> {

    @Override
    @EntityGraph("readingUploadWithReadingWithMeterWithClient")
    Page<ReadingUpload> findAll(@Nullable Specification<ReadingUpload> spec, Pageable pageable);
}
