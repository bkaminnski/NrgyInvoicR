import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../core/material.module';
import { DashboardPageComponent } from './components/dashboard-page/dashboard-page.component';

@NgModule({
  declarations: [
    DashboardPageComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class DashboardModule { }
