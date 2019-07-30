import { Component, OnInit } from '@angular/core';
import { InvoiceRunsService } from './invoice-runs.service';

@Component({
  templateUrl: './invoice-runs-page.component.html',
  providers: [
    InvoiceRunsService
  ]
})
export class InvoiceRunsPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
