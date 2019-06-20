package com.hclc.nrgyinvoicr.backend.meters.control;

import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface MetersRepository extends JpaRepository<Meter, Long>, JpaSpecificationExecutor<Meter> {

    @Override
    @EntityGraph("meterWithClient")
    Page<Meter> findAll(@Nullable Specification<Meter> spec, Pageable pageable);

    Optional<Meter> findBySerialNumber(String serialNumber);

    Optional<Meter> findBySerialNumberAndIdNot(String serialNumber, Long id);
}
