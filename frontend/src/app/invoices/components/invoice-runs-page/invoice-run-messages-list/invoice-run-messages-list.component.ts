import { Component, OnInit, Input } from '@angular/core';
import { InvoiceRunMessagesListDataSource } from './invoice-run-messages-list.datasource';
import { InvoiceRun } from 'src/app/invoices/model/invoice-run.model';
import { InvoiceRunsService } from '../invoice-runs.service';

@Component({
  selector: 'app-invoice-run-messages-list',
  templateUrl: './invoice-run-messages-list.component.html',
  styleUrls: ['./invoice-run-messages-list.component.scss']
})
export class InvoiceRunMessagesListComponent implements OnInit {
  @Input() invoiceRun: InvoiceRun;
  public dataSource: InvoiceRunMessagesListDataSource;
  public displayedColumns: string[] = ['message'];

  constructor(private invoiceRunsService: InvoiceRunsService) { }

  ngOnInit() {
    this.dataSource = new InvoiceRunMessagesListDataSource(this.invoiceRunsService, this.invoiceRun.messages);
    this.dataSource.invoiceRunMessages.subscribe(m => this.invoiceRun.messages = m);
    this.dataSource.loadInvoiceRunMessages(this.invoiceRun);
  }
}
