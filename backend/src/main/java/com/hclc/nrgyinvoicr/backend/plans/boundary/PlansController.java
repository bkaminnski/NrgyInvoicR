package com.hclc.nrgyinvoicr.backend.plans.boundary;

import com.hclc.nrgyinvoicr.backend.plans.control.PlansService;
import com.hclc.nrgyinvoicr.backend.plans.entity.Plan;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlansSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlansController {
    private final PlansService plansService;

    public PlansController(PlansService plansService) {
        this.plansService = plansService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<Plan> findPlans(PlansSearchCriteria plansSearchCriteria) {
        return plansService.findPlans(plansSearchCriteria);
    }
}
