package com.hclc.nrgyinvoicr.backend.plans.boundary;

import com.hclc.nrgyinvoicr.backend.plans.control.PlanVersionsService;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersion;
import com.hclc.nrgyinvoicr.backend.plans.entity.PlanVersionsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanVersionsController {
    private final PlanVersionsService planVersionsService;

    public PlanVersionsController(PlanVersionsService planVersionsService) {
        this.planVersionsService = planVersionsService;
    }

    @GetMapping("/{planId}/versions")
    @Transactional(readOnly = true)
    public Page<PlanVersion> findPlanVersions(@PathVariable("planId") long planId, PlanVersionsSearchCriteria plansSearchCriteria) {
        return planVersionsService.findPlanVersions(planId, plansSearchCriteria);
    }
}
