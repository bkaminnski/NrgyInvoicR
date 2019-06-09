import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MaterialModule } from './material.module';

import { NavigationComponent } from './components/navigation/navigation.component';
import { NoRecordsFoundComponent } from './components/no-records-found/no-records-found.component';

@NgModule({
  declarations: [
    NavigationComponent,
    NoRecordsFoundComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule
  ],
  exports: [
    NavigationComponent,
    NoRecordsFoundComponent
  ]
})
export class CoreModule { }
