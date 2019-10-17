package com.softwarewithpassion.nrgyinvoicr.backend.plans.control;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlanVersion;
import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlanVersionsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class PlanVersionsService {
    private final PlanVersionsRepository planVersionsRepository;
    private final ExpressionTester expressionTester;

    PlanVersionsService(PlanVersionsRepository planVersionsRepository, ExpressionTester expressionTester) {
        this.planVersionsRepository = planVersionsRepository;
        this.expressionTester = expressionTester;
    }

    public PlanVersion createPlanVersion(PlanVersion planVersion) throws InvalidExpressionException, IOException {
        planVersion.setId(null);
        validatePlanVersion(planVersion);
        return planVersionsRepository.save(planVersion);
    }

    public Page<PlanVersion> findPlanVersions(long planId, PlanVersionsSearchCriteria criteria) {
        return this.planVersionsRepository.findByPlanId(planId, criteria.getPageDefinition().asPageRequest());
    }

    public Optional<PlanVersion> updatePlanVersion(PlanVersion planVersion) throws InvalidExpressionException, IOException {
        validatePlanVersion(planVersion);
        return planVersionsRepository
                .findById(planVersion.getId())
                .map(p -> planVersionsRepository.save(planVersion));
    }

    private void validatePlanVersion(PlanVersion planVersion) throws IOException, InvalidExpressionException {
        if (!expressionTester.test(planVersion.getExpression()).isValid()) {
            throw new InvalidExpressionException();
        }
    }
}
