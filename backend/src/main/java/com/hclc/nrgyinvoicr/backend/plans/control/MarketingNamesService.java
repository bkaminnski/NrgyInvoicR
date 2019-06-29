package com.hclc.nrgyinvoicr.backend.plans.control;

import com.hclc.nrgyinvoicr.backend.plans.entity.MarketingName;
import com.hclc.nrgyinvoicr.backend.plans.entity.MarketingNamesSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MarketingNamesService {
    private final MarketingNamesRepository marketingNamesRepository;

    MarketingNamesService(MarketingNamesRepository marketingNamesRepository) {
        this.marketingNamesRepository = marketingNamesRepository;
    }

    public Page<MarketingName> findMarketingNames(MarketingNamesSearchCriteria criteria) {
        return this.marketingNamesRepository.findAll(criteria.getPageDefinition().asPageRequest());
    }
}
