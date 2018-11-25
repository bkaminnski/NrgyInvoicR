import { Component, OnInit, OnChanges } from '@angular/core';
import { Invoice } from '../../model/invoice.model';
import { InvoicesPageService } from './invoices-page.service';
import { InvoicesSearchCriteria } from './invoices-search-criteria.model';
import * as moment from 'moment';

@Component({
  templateUrl: './invoices-page.component.html',
  styleUrls: ['./invoices-page.component.scss']
})
export class InvoicesPageComponent implements OnInit {

  invoicesSearchCriteria: InvoicesSearchCriteria;
  invoices: Invoice[];
  testProp: any;

  constructor(private invoicesListService: InvoicesPageService) {
    this.invoicesSearchCriteria = new InvoicesSearchCriteria(moment().startOf('month'), moment().endOf('month'));
  }

  ngOnInit() {
    this.search();
  }

  search() {
    this.invoicesListService.findInvoices(this.invoicesSearchCriteria).subscribe(invoices => this.invoices = invoices);
  }
}
