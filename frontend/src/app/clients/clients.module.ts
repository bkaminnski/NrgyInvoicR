import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { CoreModule } from '../core/core.module';
import { ClientsPageComponent } from './components/clients-page/clients-page.component';
import { ClientsFilterComponent } from './components/clients-page/clients-filter/clients-filter.component';
import { ClientsListComponent } from './components/clients-page/clients-list/clients-list.component';
import { ClientDialogComponent } from './components/clients-page/client-dialog/client-dialog.component';
import { MetersModule } from '../meters/meters.module';

@NgModule({
  declarations: [
    ClientsPageComponent,
    ClientsFilterComponent,
    ClientsListComponent,
    ClientDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    CoreModule,
    MetersModule
  ]
})
export class ClientsModule { }
