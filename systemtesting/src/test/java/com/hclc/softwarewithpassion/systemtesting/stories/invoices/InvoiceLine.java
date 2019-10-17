package com.hclc.softwarewithpassion.systemtesting.stories.invoices;

public class InvoiceLine {
    public final String number;
    public final String description;
    public final String unitPrice;
    public final String quantity;
    public final String unit;
    public final String net;
    public final String vat;
    public final String gross;

    public InvoiceLine(String number, String description, String unitPrice, String quantity, String unit, String net, String vat, String gross) {
        this.number = number;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.unit = unit;
        this.net = net;
        this.vat = vat;
        this.gross = gross;
    }
}
