package com.softwarewithpassion.nrgyinvoicr.backend.plans.boundary;

import com.softwarewithpassion.nrgyinvoicr.backend.EntityNotFoundException;
import com.softwarewithpassion.nrgyinvoicr.backend.ErrorResponse;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.InvalidExpressionException;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.PlanVersionsService;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.control.PlansService;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.Plan;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlanVersion;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlanVersionsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/plans")
public class PlanVersionsController {
    private final PlansService plansService;
    private final PlanVersionsService planVersionsService;

    public PlanVersionsController(PlansService plansService, PlanVersionsService planVersionsService) {
        this.plansService = plansService;
        this.planVersionsService = planVersionsService;
    }

    @PostMapping("/{planId}/versions")
    @Transactional
    public ResponseEntity<PlanVersion> createPlanVersion(@PathVariable("planId") long planId, @RequestBody PlanVersion planVersion) throws InvalidExpressionException, IOException {
        Plan plan = plansService.getPlan(planId).orElseThrow(() -> new EntityNotFoundException(Plan.class, planId));
        planVersion.setPlan(plan);
        PlanVersion savedPlanVersion = planVersionsService.createPlanVersion(planVersion);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(savedPlanVersion.getId()).toUri();
        return ResponseEntity.created(uri).body(savedPlanVersion);
    }

    @GetMapping("/{planId}/versions")
    @Transactional(readOnly = true)
    public Page<PlanVersion> findPlanVersions(@PathVariable("planId") long planId, PlanVersionsSearchCriteria searchCriteria) {
        return planVersionsService.findPlanVersions(planId, searchCriteria);
    }

    @PutMapping("/{planId}/versions/{id}")
    @Transactional
    public ResponseEntity<PlanVersion> updatePlanVersion(@PathVariable("planId") long planId, @PathVariable Long id, @RequestBody PlanVersion planVersion) throws InvalidExpressionException, IOException {
        Plan plan = plansService.getPlan(planId).orElseThrow(() -> new EntityNotFoundException(Plan.class, planId));
        planVersion.setPlan(plan);
        planVersion.setId(id);
        return planVersionsService.updatePlanVersion(planVersion)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(PlanVersion.class, id));
    }

    @ExceptionHandler({InvalidExpressionException.class})
    protected ResponseEntity<ErrorResponse> handleException(InvalidExpressionException e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
