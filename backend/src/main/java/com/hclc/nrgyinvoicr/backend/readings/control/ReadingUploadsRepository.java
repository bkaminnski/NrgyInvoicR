package com.hclc.nrgyinvoicr.backend.readings.control;

import com.hclc.nrgyinvoicr.backend.readings.entity.ReadingUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingUploadsRepository extends JpaRepository<ReadingUpload, Long> {
}
