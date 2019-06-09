import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { CoreModule } from './core/core.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { InvoicesModule } from './invoices/invoices.module';
import { ReadingsModule } from './readings/readings.module';
import { MetersModule } from './meters/meters.module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MeterDialogComponent } from './meters/components/meters-page/meter-dialog/meter-dialog.component';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material/snack-bar';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    CoreModule,
    AppRoutingModule,
    DashboardModule,
    InvoicesModule,
    ReadingsModule,
    MetersModule
  ],
  providers: [
    { provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: { duration: 3500 } }
  ],
  bootstrap: [AppComponent],
  entryComponents: [MeterDialogComponent]
})
export class AppModule { }
