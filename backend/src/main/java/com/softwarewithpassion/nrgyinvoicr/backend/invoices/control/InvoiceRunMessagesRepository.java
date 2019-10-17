package com.softwarewithpassion.nrgyinvoicr.backend.invoices.control;

import com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity.InvoiceRunMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InvoiceRunMessagesRepository extends CrudRepository<InvoiceRunMessage, Long>, JpaRepository<InvoiceRunMessage, Long> {

    List<InvoiceRunMessage> findByInvoiceRunIdOrderByIdDesc(Long invoiceRunId);
}
