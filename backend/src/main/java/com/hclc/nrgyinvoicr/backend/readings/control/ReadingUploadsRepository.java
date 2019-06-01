package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingUploadsRepository extends JpaRepository<ReadingUpload, Long>, JpaSpecificationExecutor<ReadingUpload> {
}
