package com.softwarewithpassion.nrgyinvoicr.backend.plans.boundary;

import com.softwarewithpassion.nrgyinvoicr.backend.EntityNotFoundException;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.PlansService;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.Plan;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlansSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Plan> getPlan(@PathVariable long id) {
        return plansService.getPlan(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(Plan.class, id));
    }
}
