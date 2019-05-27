package com.hclc.nrgyinvoicr.backend.readings.upload;

import org.springframework.data.jpa.repository.JpaRepository;

interface ReadingUploadsRepository extends JpaRepository<ReadingUpload, Long> {
}
