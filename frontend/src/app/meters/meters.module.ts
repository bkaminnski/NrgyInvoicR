import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { MetersPageComponent } from './components/meters-page/meters-page.component';
import { MetersFilterComponent } from './components/meters-page/meters-filter/meters-filter.component';
import { MetersListComponent } from './components/meters-page/meters-list/meters-list.component';
import { MeterDialogComponent } from './components/meters-page/meter-dialog/meter-dialog.component';
import { CoreModule } from '../core/core.module';

@NgModule({
  declarations: [
    MetersPageComponent,
    MetersFilterComponent,
    MetersListComponent,
    MeterDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    CoreModule
  ]
})
export class MetersModule { }
