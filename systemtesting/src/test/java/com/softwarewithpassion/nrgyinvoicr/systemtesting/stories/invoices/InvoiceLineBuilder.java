package com.softwarewithpassion.nrgyinvoicr.systemtesting.stories.invoices;

public class InvoiceLineBuilder {
    private String number;
    private String description;
    private String unitPrice;
    private String quantity;
    private String unit;
    private String net;
    private String vat;
    private String gross;

    public static InvoiceLineBuilder anInvoiceLine() {
        return new InvoiceLineBuilder();
    }

    public InvoiceLineBuilder withNumber(String number) {
        this.number = number;
        return this;
    }

    public InvoiceLineBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public InvoiceLineBuilder withUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public InvoiceLineBuilder withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public InvoiceLineBuilder withUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public InvoiceLineBuilder withNet(String net) {
        this.net = net;
        return this;
    }

    public InvoiceLineBuilder withVat(String vat) {
        this.vat = vat;
        return this;
    }

    public InvoiceLineBuilder withGross(String gross) {
        this.gross = gross;
        return this;
    }

    public InvoiceLine build() {
        return new InvoiceLine(number, description, unitPrice, quantity, unit, net, vat, gross);
    }
}