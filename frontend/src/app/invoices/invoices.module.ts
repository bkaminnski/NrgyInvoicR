import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { InvoicesPageComponent } from './components/invoices-page/invoices-page.component';
import { InvoicesFilterComponent } from './components/invoices-page/invoices-filter/invoices-filter.component';
import { InvoicesListComponent } from './components/invoices-page/invoices-list/invoices-list.component';
import { InvoiceLinesListComponent } from './components/invoices-page/invoice-lines-list/invoice-lines-list.component';
import { InvoiceRunsPageComponent } from './components/invoice-runs-page/invoice-runs-page.component';
import { InvoiceRunsListComponent } from './components/invoice-runs-page/invoice-runs-list/invoice-runs-list.component';
import { InvoiceRunDialogComponent } from './components/invoice-runs-page/invoice-run-dialog/invoice-run-dialog.component';
import { InvoiceRunMessagesListComponent } from './components/invoice-runs-page/invoice-run-messages-list/invoice-run-messages-list.component';
import { CoreModule } from '../core/core.module';

@NgModule({
  declarations: [
    InvoicesPageComponent,
    InvoicesFilterComponent,
    InvoicesListComponent,
    InvoiceLinesListComponent,
    InvoiceRunsPageComponent,
    InvoiceRunsListComponent,
    InvoiceRunDialogComponent,
    InvoiceRunMessagesListComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    CoreModule
  ],
  providers: []
})
export class InvoicesModule { }
