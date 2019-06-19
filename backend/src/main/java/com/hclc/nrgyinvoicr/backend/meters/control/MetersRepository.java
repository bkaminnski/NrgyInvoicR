package com.hclc.nrgyinvoicr.backend.meters.control;

import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MetersRepository extends JpaRepository<Meter, Long>, JpaSpecificationExecutor<Meter> {

    Optional<Meter> findBySerialNumber(String serialNumber);

    Optional<Meter> findBySerialNumberAndIdNot(String serialNumber, Long id);
}
