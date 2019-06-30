import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardPageComponent } from './dashboard/components/dashboard-page/dashboard-page.component';
import { InvoicesPageComponent } from './invoices/components/invoices-page/invoices-page.component';
import { PlansPageComponent } from './plans/components/plans-page/plans-page.component';
import { PlanPageComponent } from './plans/components/plan-page/plan-page.component';
import { ReadingsUploadPageComponent } from './readings/components/readings-upload-page/readings-upload-page.component';
import { CanDeactivateGuard } from './core/can-deactivate.guard';
import { ReadingsUploadsHistoryPageComponent } from './readings/components/readings-uploads-history-page/readings-uploads-history-page.component';
import { MetersPageComponent } from './meters/components/meters-page/meters-page.component';
import { ClientsPageComponent } from './clients/components/clients-page/clients-page.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardPageComponent },
  { path: 'invoices', component: InvoicesPageComponent },
  { path: 'plans/:id', component: PlanPageComponent },
  { path: 'plans', component: PlansPageComponent },
  { path: 'readingsUpload', component: ReadingsUploadPageComponent, canDeactivate: [CanDeactivateGuard] },
  { path: 'readingsUploadsHistory', component: ReadingsUploadsHistoryPageComponent },
  { path: 'meters', component: MetersPageComponent },
  { path: 'clients', component: ClientsPageComponent },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
