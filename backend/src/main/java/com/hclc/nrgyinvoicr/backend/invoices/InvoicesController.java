package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/invoices"})
public class InvoicesController {
    private final InvoicesService invoicesService;

    public InvoicesController(InvoicesService invoicesService) {
        this.invoicesService = invoicesService;
    }

    @GetMapping
    public Iterable<Invoice> findInvoices(InvoicesSearchCriteria invoicesSearchCriteria) {
        return invoicesService.findInvoices(invoicesSearchCriteria);
    }
}
