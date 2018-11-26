import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { InvoicesPageComponent } from './components/invoices-page/invoices-page.component';
import { InvoicesListService } from './components/invoices-page/invoices-list/invoices-list.service';
import { InvoicesFilterComponent } from './components/invoices-page/invoices-filter/invoices-filter.component';
import { InvoicesListComponent } from './components/invoices-page/invoices-list/invoices-list.component';

@NgModule({
  declarations: [
    InvoicesPageComponent,
    InvoicesFilterComponent,
    InvoicesListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule
  ],
  providers: [
    InvoicesListService
  ]
})
export class InvoicesModule { }
