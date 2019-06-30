package com.hclc.nrgyinvoicr.backend.plans.control;

import com.hclc.nrgyinvoicr.backend.plans.entity.Plan;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlansSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PlansService {
    private final PlansRepository plansRepository;

    PlansService(PlansRepository plansRepository) {
        this.plansRepository = plansRepository;
    }

    public Page<Plan> findPlans(PlansSearchCriteria criteria) {
        return this.plansRepository.findAll(criteria.getPageDefinition().asPageRequest());
    }
}
