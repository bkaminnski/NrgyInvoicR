import { Component, OnInit, OnChanges } from '@angular/core';
import { Invoice } from '../../model/invoice.model';
import { InvoicesPageService } from './invoices-page.service';
import { InvoicesSearchCriteria } from './invoices-search-criteria.model';

@Component({
  templateUrl: './invoices-page.component.html',
  styleUrls: ['./invoices-page.component.scss']
})
export class InvoicesPageComponent implements OnInit {

  invoicesSearchCriteria: InvoicesSearchCriteria;
  invoices: Invoice[];
  testProp: any;

  constructor(private invoicesListService: InvoicesPageService) {
    const date = new Date();
    this.invoicesSearchCriteria = new InvoicesSearchCriteria(new Date(date.getFullYear(), date.getMonth(), 1), new Date(date.getFullYear(), date.getMonth() + 1, 0));
  }

  ngOnInit() {
    this.search();
  }

  search() {
    this.invoicesListService.findInvoices(this.invoicesSearchCriteria).subscribe(invoices => this.invoices = invoices);
  }

  parseDate(dateString: string): Date {
    if (dateString) {
      return new Date(dateString);
    } else {
      return null;
    }
  }
}
