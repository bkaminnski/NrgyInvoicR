package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface InvoicesRepository extends CrudRepository<Invoice, Long>, JpaSpecificationExecutor {
}
