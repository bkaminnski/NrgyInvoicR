import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard/components/dashboard/dashboard.component';
import { InvoicesPageComponent } from './invoices/components/invoices-page/invoices-page.component';
import { ReadingsUploadPageComponent } from './readings/components/readings-upload-page/readings-upload-page.component';
import { CanDeactivateGuard } from './core/can-deactivate.guard';
import { ReadingsUploadHistoryPageComponent } from './readings/components/readings-upload-history-page/readings-upload-history-page/readings-upload-history-page.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'invoices', component: InvoicesPageComponent },
  { path: 'readingsUpload', component: ReadingsUploadPageComponent, canDeactivate: [CanDeactivateGuard] },
  { path: 'readingsUploadHistory', component: ReadingsUploadHistoryPageComponent },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
