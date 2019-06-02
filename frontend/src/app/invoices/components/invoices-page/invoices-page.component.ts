import { Component, OnInit } from '@angular/core';
import { InvoicesListService } from './invoices-list/invoices-list.service';

@Component({
  templateUrl: './invoices-page.component.html',
  styles: [],
  providers: [
    InvoicesListService
  ]
})
export class InvoicesPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
