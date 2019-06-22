package com.hclc.nrgyinvoicr.backend.plans.entity;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class FixedFees {
    private BigDecimal subscriptionFee;
    private BigDecimal networkFee;
}
