package com.hclc.nrgyinvoicr.backend.plans.boundary;

import com.hclc.nrgyinvoicr.backend.plans.control.MarketingNamesService;
import com.hclc.nrgyinvoicr.backend.plans.entity.MarketingName;
import com.hclc.nrgyinvoicr.backend.plans.entity.MarketingNamesSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/marketingNames")
public class MarketingNamesController {
    private final MarketingNamesService marketingNamesService;

    public MarketingNamesController(MarketingNamesService marketingNamesService) {
        this.marketingNamesService = marketingNamesService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<MarketingName> findMarketingNames(MarketingNamesSearchCriteria marketingNamesSearchCriteria) {
        return marketingNamesService.findMarketingNames(marketingNamesSearchCriteria);
    }
}
