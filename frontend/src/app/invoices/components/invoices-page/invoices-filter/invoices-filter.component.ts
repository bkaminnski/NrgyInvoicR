import { Component, OnInit, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { InvoicesSearchCriteria } from 'src/app/invoices/model/invoices-search-criteria.model';
import * as moment from 'moment';

@Component({
  selector: 'app-invoices-filter',
  templateUrl: './invoices-filter.component.html',
  styleUrls: ['./invoices-filter.component.scss']
})
export class InvoicesFilterComponent implements OnInit, AfterViewInit {
  @Output() searchEvent = new EventEmitter();
  invoicesSearchCriteria: InvoicesSearchCriteria;

  constructor() {
    this.invoicesSearchCriteria = new InvoicesSearchCriteria(moment().startOf('month'), moment().endOf('month').startOf('day'));
  }

  ngOnInit() { }

  ngAfterViewInit() {
    setTimeout(() => this.search());
  }

  search() {
    this.searchEvent.emit(this.invoicesSearchCriteria);
  }
}
