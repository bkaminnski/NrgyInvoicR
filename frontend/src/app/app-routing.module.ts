import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardPageComponent } from './dashboard/components/dashboard-page/dashboard-page.component';
import { InvoicesPageComponent } from './invoices/components/invoices-page/invoices-page.component';
import { PlansPageComponent } from './plans/components/plans-page/plans-page.component';
import { PlanVersionsPageComponent } from './plans/components/plan-versions-page/plan-versions-page.component';
import { PlanResolverService } from './plans/components/plan-resolver.service';
import { ReadingsUploadPageComponent } from './readings/components/readings-upload-page/readings-upload-page.component';
import { CanDeactivateGuard } from './core/can-deactivate.guard';
import { ReadingsUploadsHistoryPageComponent } from './readings/components/readings-uploads-history-page/readings-uploads-history-page.component';
import { MetersPageComponent } from './meters/components/meters-page/meters-page.component';
import { ClientsPageComponent } from './clients/components/clients-page/clients-page.component';
import { ClientPlanAssignmentsPageComponent } from './clients/components/client-plan-assignments-page/client-plan-assignments-page.component';
import { ClientResolverService } from './clients/components/client-resolver.service';

const routes: Routes = [
  { path: 'dashboard', component: DashboardPageComponent },
  { path: 'invoices', component: InvoicesPageComponent },
  {
    path: 'plans/:id/versions', component: PlanVersionsPageComponent, resolve: {
      plan: PlanResolverService
    }
  },
  { path: 'plans', component: PlansPageComponent },
  { path: 'readingsUpload', component: ReadingsUploadPageComponent, canDeactivate: [CanDeactivateGuard] },
  { path: 'readingsUploadsHistory', component: ReadingsUploadsHistoryPageComponent },
  { path: 'meters', component: MetersPageComponent },
  {
    path: 'clients/:id/planAssignments', component: ClientPlanAssignmentsPageComponent, resolve: {
      client: ClientResolverService
    }
  },
  { path: 'clients', component: ClientsPageComponent },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
