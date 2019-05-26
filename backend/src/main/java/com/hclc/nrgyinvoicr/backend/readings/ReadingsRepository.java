package com.hclc.nrgyinvoicr.backend.readings;

import org.springframework.data.jpa.repository.JpaRepository;

interface ReadingsRepository extends JpaRepository<Reading, Long> {
}
