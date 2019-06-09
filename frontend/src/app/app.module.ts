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
import { NotificationComponent } from './core/components/notification/notification.component';

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
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [MeterDialogComponent, NotificationComponent]
})
export class AppModule { }
