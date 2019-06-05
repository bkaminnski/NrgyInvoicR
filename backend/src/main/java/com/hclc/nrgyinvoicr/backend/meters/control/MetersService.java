package com.hclc.nrgyinvoicr.backend.meters.control;

import com.hclc.nrgyinvoicr.backend.meters.entity.Meter;
import com.hclc.nrgyinvoicr.backend.meters.entity.MetersSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.hclc.nrgyinvoicr.backend.meters.entity.MetersSpecification.serialNumberLike;

@Service
public class MetersService {
    private final MetersRepository metersRepository;

    MetersService(MetersRepository metersRepository) {
        this.metersRepository = metersRepository;
    }

    public Page<Meter> findMeters(MetersSearchCriteria criteria) {
        String serialNumber = criteria.getSerialNumber();
        Specification<Meter> specification = serialNumberLike(serialNumber);
        return this.metersRepository.findAll(specification, criteria.getPageDefinition().asPageRequest());
    }
}
