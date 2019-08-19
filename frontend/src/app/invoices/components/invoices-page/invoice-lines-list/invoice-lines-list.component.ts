import { Component, OnInit, Input } from '@angular/core';
import { Invoice } from 'src/app/invoices/model/invoice.model';
import { InvoiceLinesListDataSource } from './invoice-lines-list.datasource';

@Component({
  selector: 'app-invoice-lines-list',
  templateUrl: './invoice-lines-list.component.html',
  styleUrls: ['./invoice-lines-list.component.scss']
})
export class InvoiceLinesListComponent implements OnInit {
  @Input() invoice: Invoice;
  public dataSource: InvoiceLinesListDataSource;
  public displayedColumns: string[] = ['number', 'description', 'unitPrice', 'quantity', 'unit', 'netTotal', 'vat', 'grossTotal'];

  constructor() { }

  ngOnInit() {
    this.dataSource = new InvoiceLinesListDataSource(this.invoice.invoiceLines);
  }
}
