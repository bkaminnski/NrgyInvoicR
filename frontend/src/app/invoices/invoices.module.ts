import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { InvoicesPageComponent } from './components/invoices-page/invoices-page.component';
import { InvoicesPageService } from './components/invoices-page/invoices-page.service';

@NgModule({
  declarations: [
    InvoicesPageComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule
  ],
  providers: [
    InvoicesPageService
  ]
})
export class InvoicesModule { }
