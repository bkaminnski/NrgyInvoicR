package com.hclc.nrgyinvoicr.backend.readings;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface MetersRepository extends JpaRepository<Meter, Long> {

    Optional<Meter> findByExternalId(String externalId);
}
