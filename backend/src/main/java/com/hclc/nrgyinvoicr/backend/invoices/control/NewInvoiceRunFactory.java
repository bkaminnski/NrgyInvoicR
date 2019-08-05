package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.NrgyInvoicRConfig;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZonedDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
class NewInvoiceRunFactory {
    private final Clock clock;
    private final NrgyInvoicRConfig nrgyInvoicRConfig;
    private final InvoicesRepository invoicesRepository;

    NewInvoiceRunFactory(Clock clock, NrgyInvoicRConfig nrgyInvoicRConfig, InvoicesRepository invoicesRepository) {
        this.clock = clock;
        this.nrgyInvoicRConfig = nrgyInvoicRConfig;
        this.invoicesRepository = invoicesRepository;
    }

    InvoiceRun createNewInvoiceRun() {
        Instant referenceInstant = clock.instant();
        InvoiceRun invoiceRun = new InvoiceRun();
        invoiceRun.setIssueDate(prepareIssueDate(referenceInstant));
        invoiceRun.setSinceClosed(prepareSinceClosed(referenceInstant));
        invoiceRun.setUntilOpen(prepareUntilOpen(prepareSinceClosed(referenceInstant)));
        invoiceRun.setFirstInvoiceNumber(((int) invoicesRepository.count()) + 1);
        invoiceRun.setNumberTemplate(nrgyInvoicRConfig.getInvoiceNumberTemplate());
        return invoiceRun;
    }

    private ZonedDateTime prepareIssueDate(Instant referenceInstant) {
        ZonedDateTime issueDate = referenceInstant.atZone(nrgyInvoicRConfig.getTimeZoneAsZoneId()).withDayOfMonth(10).truncatedTo(DAYS);
        if (issueDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            issueDate = issueDate.plusDays(2);
        } else if (issueDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            issueDate = issueDate.plusDays(1);
        }
        return issueDate;
    }

    private ZonedDateTime prepareSinceClosed(Instant referenceInstant) {
        return referenceInstant.atZone(nrgyInvoicRConfig.getTimeZoneAsZoneId()).withDayOfMonth(1).truncatedTo(DAYS).minusMonths(1);
    }

    private ZonedDateTime prepareUntilOpen(ZonedDateTime sinceClosed) {
        return sinceClosed.plusMonths(1);
    }
}
