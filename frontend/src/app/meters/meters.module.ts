import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { CoreModule } from '../core/core.module';
import { MetersPageComponent } from './components/meters-page/meters-page.component';
import { MetersFilterComponent } from './components/meters-page/meters-filter/meters-filter.component';
import { MetersListComponent } from './components/meters-page/meters-list/meters-list.component';
import { MeterDialogComponent } from './components/meters-page/meter-dialog/meter-dialog.component';
import { MeterAutocompleteComponent } from './components/meter-autocomplete/meter-autocomplete.component';

@NgModule({
  declarations: [
    MetersPageComponent,
    MetersFilterComponent,
    MetersListComponent,
    MeterDialogComponent,
    MeterAutocompleteComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    CoreModule
  ],
  exports: [
    MeterAutocompleteComponent
  ]
})
export class MetersModule { }
