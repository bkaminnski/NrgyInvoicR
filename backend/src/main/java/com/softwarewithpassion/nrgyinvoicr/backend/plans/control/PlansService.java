package com.softwarewithpassion.nrgyinvoicr.backend.plans.control;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.Plan;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlansSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlansSpecification.nameLike;

@Service
public class PlansService {
    private final PlansRepository plansRepository;

    PlansService(PlansRepository plansRepository) {
        this.plansRepository = plansRepository;
    }

    public Optional<Plan> getPlan(long planId) {
        return this.plansRepository.findById(planId);
    }

    public Page<Plan> findPlans(PlansSearchCriteria criteria) {
        String name = criteria.getName();
        return this.plansRepository.findAll(nameLike(name), criteria.getPageDefinition().asPageRequest());
    }
}
