package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.EntityNotFoundException;
import com.hclc.nrgyinvoicr.backend.invoices.control.generation.InvoicesGenerationStarter;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunMessage;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceRunsService {
    private final InvoiceRunsRepository invoiceRunsRepository;
    private final InvoiceRunMessagesRepository invoiceRunMessagesRepository;
    private final NewInvoiceRunFactory newInvoiceRunFactory;
    private final InvoicesGenerationStarter invoiceGenerationStarter;

    InvoiceRunsService(InvoiceRunsRepository invoiceRunsRepository, InvoiceRunMessagesRepository invoiceRunMessagesRepository, NewInvoiceRunFactory newInvoiceRunFactory, InvoicesGenerationStarter invoiceGenerationStarter) {
        this.invoiceRunsRepository = invoiceRunsRepository;
        this.invoiceRunMessagesRepository = invoiceRunMessagesRepository;
        this.newInvoiceRunFactory = newInvoiceRunFactory;
        this.invoiceGenerationStarter = invoiceGenerationStarter;
    }

    public InvoiceRun prepareNewInvoiceRun() {
        return newInvoiceRunFactory.createNewInvoiceRun();
    }

    public InvoiceRun createInvoiceRun(InvoiceRun invoiceRun) {
        invoiceRun.setId(null);
        return invoiceRunsRepository.save(invoiceRun);
    }

    public Optional<InvoiceRun> getInvoiceRun(Long id) {
        return invoiceRunsRepository.findById(id);
    }

    public List<InvoiceRunMessage> getInvoiceRunMessages(Long id) {
        return invoiceRunMessagesRepository.findByInvoiceRunIdOrderByIdDesc(id);
    }

    public Page<InvoiceRun> findInvoiceRuns(InvoiceRunsSearchCriteria criteria) {
        return this.invoiceRunsRepository.findAll(criteria.getPageDefinition().asPageRequest());
    }

    public InvoiceRun start(Long id) {
        InvoiceRun invoiceRun = invoiceRunsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(InvoiceRun.class, id));
        return invoiceGenerationStarter.start(invoiceRun);
    }
}
