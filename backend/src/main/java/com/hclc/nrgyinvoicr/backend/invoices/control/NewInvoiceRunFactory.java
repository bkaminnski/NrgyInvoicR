package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.NrgyInvoicRConfig;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.springframework.stereotype.Component;

import java.time.*;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
class NewInvoiceRunFactory {
    private final Clock clock;
    private final NrgyInvoicRConfig nrgyInvoicRConfig;

    NewInvoiceRunFactory(Clock clock, NrgyInvoicRConfig nrgyInvoicRConfig) {
        this.clock = clock;
        this.nrgyInvoicRConfig = nrgyInvoicRConfig;
    }

    InvoiceRun createNewInvoiceRun() {
        Instant referenceInstant = clock.instant();
        InvoiceRun invoiceRun = new InvoiceRun();
        invoiceRun.setIssueDate(prepareIssueDate(referenceInstant));
        invoiceRun.setSinceClosed(prepareSinceClosed(referenceInstant));
        invoiceRun.setUntilOpen(prepareUntilOpen(prepareSinceClosed(referenceInstant)));
        return invoiceRun;
    }

    private LocalDate prepareIssueDate(Instant referenceInstant) {
        LocalDate issueDate = referenceInstant.atZone(nrgyInvoicRConfig.getTimeZoneAsZoneId()).toLocalDate().atStartOfDay().withDayOfMonth(10).toLocalDate();
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
