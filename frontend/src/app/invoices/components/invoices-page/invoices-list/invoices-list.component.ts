import { Component, OnInit } from '@angular/core';
import { InvoicesSearchCriteria } from 'src/app/invoices/model/invoices-search-criteria.model';
import { InvoicesListService } from './invoices-list.service';
import { Invoice } from 'src/app/invoices/model/invoice.model';

@Component({
  selector: 'app-invoices-list',
  templateUrl: './invoices-list.component.html',
  styleUrls: ['./invoices-list.component.scss']
})
export class InvoicesListComponent implements OnInit {

  invoices: Invoice[];

  constructor(private invoicesListService: InvoicesListService) { }

  ngOnInit() { }

  search(invoicesSearchCriteria: InvoicesSearchCriteria) {
    this.invoicesListService.findInvoices(invoicesSearchCriteria).subscribe(invoices => this.invoices = invoices);
  }
}
