package com.hclc.nrgyinvoicr.backend.meters.control;

import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MetersRepository extends JpaRepository<Meter, Long> {

    Optional<Meter> findByExternalId(String externalId);
}
