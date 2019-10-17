package com.softwarewithpassion.nrgyinvoicr.backend.plans.control;

import com.softwarewithpassion.nrgyinvoicr.backend.plans.entity.PlanVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface PlanVersionsRepository extends JpaRepository<PlanVersion, Long> {

    Page<PlanVersion> findByPlanId(Long planId, Pageable pageable);
}
