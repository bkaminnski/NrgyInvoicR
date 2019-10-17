package com.softwarewithpassion.nrgyinvoicr.backend.invoices.control;

import com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

public interface InvoicesRepository extends CrudRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {

    @Override
    @EntityGraph("invoiceWithInvoiceRunAndClientAndPlanVersion")
    Page<Invoice> findAll(@Nullable Specification<Invoice> spec, Pageable pageable);
}
