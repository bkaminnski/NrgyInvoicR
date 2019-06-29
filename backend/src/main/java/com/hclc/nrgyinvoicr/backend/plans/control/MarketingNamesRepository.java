package com.hclc.nrgyinvoicr.backend.plans.control;

import com.hclc.nrgyinvoicr.backend.plans.entity.MarketingName;
import org.springframework.data.jpa.repository.JpaRepository;

interface MarketingNamesRepository extends JpaRepository<MarketingName, Long> {
}
