package com.hclc.nrgyinvoicr.backend.invoices;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class InvoicesService {

    public List<Invoice> findInvoices(InvoicesSearchCriteria invoicesSearchCriteria) throws ParseException {
        return Stream.of(
                new Invoice(1L, "1/11/2018", new SimpleDateFormat("dd/MM/yyyy").parse("01/11/2018")),
                new Invoice(2L, "2/11/2018", new SimpleDateFormat("dd/MM/yyyy").parse("15/11/2018")),
                new Invoice(3L, "1/02/2018", new SimpleDateFormat("dd/MM/yyyy").parse("07/02/2018"))
        )
                .filter(i -> i.matches(invoicesSearchCriteria))
                .collect(toList());
    }
}
