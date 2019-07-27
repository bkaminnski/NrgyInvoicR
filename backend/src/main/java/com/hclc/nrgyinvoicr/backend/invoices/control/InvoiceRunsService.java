package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRunsSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.time.ZoneId.systemDefault;

@Service
public class InvoiceRunsService {
    private final Clock clock;
    private final InvoiceRunsRepository invoiceRunsRepository;

    InvoiceRunsService(Clock clock, InvoiceRunsRepository invoiceRunsRepository) {
        this.clock = clock;
        this.invoiceRunsRepository = invoiceRunsRepository;
    }

    public InvoiceRun createInvoiceRun(InvoiceRun invoiceRun) {
        invoiceRun.setId(null);
        return invoiceRunsRepository.save(invoiceRun);
    }

    public Page<InvoiceRun> findInvoiceRuns(InvoiceRunsSearchCriteria criteria) {
        return this.invoiceRunsRepository.findAll(criteria.getPageDefinition().asPageRequest());
    }

    public InvoiceRun prepareNewInvoiceRun() {
        Instant referenceInstant = clock.instant();
        InvoiceRun invoiceRun = new InvoiceRun();
        invoiceRun.setIssueDate(prepareIssueDate(referenceInstant));
        invoiceRun.setSinceClosed(prepareSinceClosed(referenceInstant));
        invoiceRun.setUntilOpen(prepareUntilOpen(prepareSinceClosed(referenceInstant)));
        return invoiceRun;
    }

    private LocalDate prepareIssueDate(Instant referenceInstant) {
        LocalDate issueDate = referenceInstant.atZone(systemDefault()).toLocalDate().atStartOfDay().withDayOfMonth(10).toLocalDate();
        if (issueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            issueDate = issueDate.plusDays(2);
        } else if (issueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            issueDate = issueDate.plusDays(1);
        }
        return issueDate;
    }

    private ZonedDateTime prepareSinceClosed(Instant referenceInstant) {
        return referenceInstant.atZone(systemDefault()).withDayOfMonth(1).truncatedTo(ChronoUnit.DAYS).minusMonths(1);
    }

    private ZonedDateTime prepareUntilOpen(ZonedDateTime sinceClosed) {
        return sinceClosed.plusMonths(1);
    }
}
