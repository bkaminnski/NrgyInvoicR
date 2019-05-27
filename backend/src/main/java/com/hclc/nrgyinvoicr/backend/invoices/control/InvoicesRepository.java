package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.invoices.entity.Invoice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

interface InvoicesRepository extends CrudRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
}
