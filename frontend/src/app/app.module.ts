import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { CoreModule } from './core/core.module';
import { AppRoutingModule } from './app-routing.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { InvoicesModule } from './invoices/invoices.module';
import { PlansModule } from './plans/plans.module';
import { ReadingsModule } from './readings/readings.module';
import { MetersModule } from './meters/meters.module';
import { ClientsModule } from './clients/clients.module';

import { AppComponent } from './app.component';
import { NotificationComponent } from './core/components/notification/notification.component';
import { MeterDialogComponent } from './meters/components/meters-page/meter-dialog/meter-dialog.component';
import { ClientDialogComponent } from './clients/components/clients-page/client-dialog/client-dialog.component';

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
    PlansModule,
    ReadingsModule,
    MetersModule,
    ClientsModule
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    NotificationComponent,
    MeterDialogComponent,
    ClientDialogComponent
  ]
})
export class AppModule { }
