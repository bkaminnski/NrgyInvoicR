package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

interface InvoiceRunsRepository extends CrudRepository<InvoiceRun, Long>, JpaRepository<InvoiceRun, Long> {
}
