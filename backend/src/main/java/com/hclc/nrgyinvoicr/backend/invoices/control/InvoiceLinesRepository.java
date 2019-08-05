package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceLine;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceLinesRepository extends CrudRepository<InvoiceLine, Long>, JpaSpecificationExecutor<InvoiceLine> {
}
