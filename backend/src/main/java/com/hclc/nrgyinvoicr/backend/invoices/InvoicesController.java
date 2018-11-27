package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({"/api/invoices"})
public class InvoicesController {

    private final InvoicesService invoicesService;

    @Autowired
    public InvoicesController(InvoicesService invoicesService) {
        this.invoicesService = invoicesService;
    }

    @GetMapping
    public Iterable<Invoice> findInvoices(InvoicesSearchCriteria invoicesSearchCriteria) throws ParseException {
        return invoicesService.findInvoices(invoicesSearchCriteria);
    }
}
