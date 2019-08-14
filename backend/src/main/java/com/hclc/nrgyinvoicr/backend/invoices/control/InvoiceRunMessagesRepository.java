package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRunMessagesRepository extends CrudRepository<InvoiceRunMessage, Long>, JpaRepository<InvoiceRunMessage, Long> {
}
