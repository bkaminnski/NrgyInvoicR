package com.hclc.nrgyinvoicr.backend.invoices.control;

import com.hclc.nrgyinvoicr.backend.NrgyInvoicRConfig;
import com.hclc.nrgyinvoicr.backend.invoices.entity.InvoiceRun;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import static com.hclc.nrgyinvoicr.backend.DateTimeFormat.ISO_8601_DATE;
import static java.time.Clock.fixed;
import static java.time.LocalDate.parse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NewInvoiceRunFactoryTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ISO_8601_DATE);
    private static final NrgyInvoicRConfig config = new NrgyInvoicRConfig() {{
        setTimeZone("Europe/Berlin");
        setInvoiceNumberTemplate("XYZ");
    }};

    @ParameterizedTest(name = "{0}")
    @MethodSource("parameters")
    void shouldCreateNewInvoiceRun(String description, String today, String issueDate, String sinceClosed, String untilOpen) {
        Clock clock = fixed(parse(today, formatter).atStartOfDay().atZone(config.getTimeZoneAsZoneId()).toInstant(), config.getTimeZoneAsZoneId());
        InvoicesRepository invoicesRepository = mock(InvoicesRepository.class);
        when(invoicesRepository.count()).thenReturn(1000L);
        NewInvoiceRunFactory newInvoiceRunFactory = new NewInvoiceRunFactory(clock, config, invoicesRepository);

        InvoiceRun newInvoiceRun = newInvoiceRunFactory.createNewInvoiceRun();

        assertThat(newInvoiceRun.getIssueDate()).isEqualTo(parse(issueDate, formatter).atStartOfDay().atZone(config.getTimeZoneAsZoneId()));
        assertThat(newInvoiceRun.getSinceClosed()).isEqualTo(parse(sinceClosed, formatter).atStartOfDay().atZone(config.getTimeZoneAsZoneId()));
        assertThat(newInvoiceRun.getUntilOpen()).isEqualTo(parse(untilOpen, formatter).atStartOfDay().atZone(config.getTimeZoneAsZoneId()));
        assertThat(newInvoiceRun.getFirstInvoiceNumber()).isEqualTo(1001);
        assertThat(newInvoiceRun.getNumberTemplate()).isEqualTo("XYZ");
    }

    private static Stream<Arguments> parameters() {
        return Stream.of(
                of("10th day of month is Saturday, issue date postponed to Monday", "2019-08-08", "2019-08-12", "2019-07-01", "2019-08-01"),
                of("10th day of month is Sunday, issue date postponed to Monday", "2019-03-06", "2019-03-11", "2019-02-01", "2019-03-01"),
                of("10th day of month is Friday, issue date left on Friday", "2020-01-07", "2020-01-10", "2019-12-01", "2020-01-01")
        );
    }
}