package com.hclc.nrgyinvoicr.backend.plans.control;

import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersion;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersionsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class PlanVersionsService {
    private final PlanVersionsRepository planVersionsRepository;

    PlanVersionsService(PlanVersionsRepository planVersionsRepository) {
        this.planVersionsRepository = planVersionsRepository;
    }

    public Page<PlanVersion> findPlanVersions(long planId, PlanVersionsSearchCriteria criteria) {
        return this.planVersionsRepository.findByPlanId(planId, criteria.getPageDefinition().asPageRequest());
    }
}
