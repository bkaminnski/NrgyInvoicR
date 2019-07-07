package com.hclc.nrgyinvoicr.backend.plans.control;

import com.hclc.nrgyinvoicr.backend.plans.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

interface PlansRepository extends JpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {
}
