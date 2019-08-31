import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

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
import { PlanVersionDialogComponent } from './plans/components/plan-versions-page/plan-version-dialog/plan-version-dialog.component';
import { ClientPlanAssignmentDialogComponent } from './clients/components/client-plan-assignments-page/client-plan-assignment-dialog/client-plan-assignment-dialog.component';
import { InvoiceRunDialogComponent } from './invoices/components/invoice-runs-page/invoice-run-dialog/invoice-run-dialog.component';
import { AuthenticationInterceptor } from './core/authentication.interceptor';

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
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    NotificationComponent,
    MeterDialogComponent,
    ClientDialogComponent,
    PlanVersionDialogComponent,
    ClientPlanAssignmentDialogComponent,
    InvoiceRunDialogComponent
  ]
})
export class AppModule { }
