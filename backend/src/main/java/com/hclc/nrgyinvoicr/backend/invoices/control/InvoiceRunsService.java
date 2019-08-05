package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.EntityNotFoundException;
import com.hclc.nrgyinvoicr.backend.invoices.control.generation.InvoiceGenerationService;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class InvoiceRunsService {
    private final InvoiceRunsRepository invoiceRunsRepository;
    private final NewInvoiceRunFactory newInvoiceRunFactory;
    private final InvoiceGenerationService invoiceGenerationService;

    InvoiceRunsService(InvoiceRunsRepository invoiceRunsRepository, NewInvoiceRunFactory newInvoiceRunFactory, InvoiceGenerationService invoiceGenerationService) {
        this.invoiceRunsRepository = invoiceRunsRepository;
        this.newInvoiceRunFactory = newInvoiceRunFactory;
        this.invoiceGenerationService = invoiceGenerationService;
    }

    public InvoiceRun prepareNewInvoiceRun() {
        return newInvoiceRunFactory.createNewInvoiceRun();
    }

    public InvoiceRun createInvoiceRun(InvoiceRun invoiceRun) {
        invoiceRun.setId(null);
        return invoiceRunsRepository.save(invoiceRun);
    }

    public Page<InvoiceRun> findInvoiceRuns(InvoiceRunsSearchCriteria criteria) {
        return this.invoiceRunsRepository.findAll(criteria.getPageDefinition().asPageRequest());
    }

    public void start(Long id) throws Exception {
        InvoiceRun invoiceRun = invoiceRunsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(InvoiceRun.class, id));
        invoiceGenerationService.start(invoiceRun);
    }
}
