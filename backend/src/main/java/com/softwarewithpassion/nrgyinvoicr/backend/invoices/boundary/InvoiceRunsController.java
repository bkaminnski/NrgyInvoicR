package com.softwarewithpassion.nrgyinvoicr.backend.invoices.boundary;

import com.softwarewithpassion.nrgyinvoicr.backend.EntityNotFoundException;
import com.softwarewithpassion.nrgyinvoicr.backend.ErrorResponse;
import com.softwarewithpassion.nrgyinvoicr.backend.invoices.control.InvoiceRunsService;
import com.softwarewithpassion.nrgyinvoicr.backend.invoices.control.generation.ErrorCompilingInvoicePrintoutTemplate;
import com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity.InvoiceRunMessage;
import com.softwarewithpassion.nrgyinvoicr.backend.invoices.entity.InvoiceRunsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/invoiceRuns")
public class InvoiceRunsController {
    private final InvoiceRunsService invoiceRunsService;

    public InvoiceRunsController(InvoiceRunsService invoiceRunsService) {
        this.invoiceRunsService = invoiceRunsService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<InvoiceRun> createInvoiceRun(@RequestBody InvoiceRun invoiceRun) {
        InvoiceRun savedInvoiceRun = invoiceRunsService.createInvoiceRun(invoiceRun);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(savedInvoiceRun.getId()).toUri();
        return ResponseEntity.created(uri).body(savedInvoiceRun);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<InvoiceRun> getInvoiceRun(@PathVariable Long id) {
        return invoiceRunsService.getInvoiceRun(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException(InvoiceRun.class, id));
    }

    @GetMapping("/{id}/messages")
    @Transactional(readOnly = true)
    public ResponseEntity<List<InvoiceRunMessage>> getInvoiceRunMessages(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceRunsService.getInvoiceRunMessages(id));
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<InvoiceRun> findInvoiceRuns(InvoiceRunsSearchCriteria invoiceRunsSearchCriteria) {
        return invoiceRunsService.findInvoiceRuns(invoiceRunsSearchCriteria);
    }

    @GetMapping("/new")
    @Transactional(readOnly = true)
    public InvoiceRun prepareNewInvoiceRun() {
        return invoiceRunsService.prepareNewInvoiceRun();
    }

    @PostMapping("/{id}/start")
    @Transactional(readOnly = true)
    public ResponseEntity<InvoiceRun> start(@PathVariable Long id) throws ErrorCompilingInvoicePrintoutTemplate {
        InvoiceRun invoiceRun = invoiceRunsService.start(id);
        return ok(invoiceRun);
    }

    @ExceptionHandler({ErrorCompilingInvoicePrintoutTemplate.class})
    protected ResponseEntity<ErrorResponse> handleException(ErrorCompilingInvoicePrintoutTemplate e) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }
}
