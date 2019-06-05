package com.hclc.nrgyinvoicr.backend.meters.control;

import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MetersRepository extends CrudRepository<Meter, Long>, JpaSpecificationExecutor<Meter> {

    Optional<Meter> findBySerialNumber(String serialNumber);
}
