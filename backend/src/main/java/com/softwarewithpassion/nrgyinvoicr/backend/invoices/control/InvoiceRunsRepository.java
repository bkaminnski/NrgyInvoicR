package com.softwarewithpassion.nrgyinvoicr.backend.invoices.control;

import com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRunsRepository extends CrudRepository<InvoiceRun, Long>, JpaRepository<InvoiceRun, Long> {
}
