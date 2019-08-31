import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginPageComponent } from './core/components/login-page/login-page.component';
import { DashboardPageComponent } from './dashboard/components/dashboard-page/dashboard-page.component';
import { InvoiceRunsPageComponent } from './invoices/components/invoice-runs-page/invoice-runs-page.component';
import { InvoicesPageComponent } from './invoices/components/invoices-page/invoices-page.component';
import { PlansPageComponent } from './plans/components/plans-page/plans-page.component';
import { PlanVersionsPageComponent } from './plans/components/plan-versions-page/plan-versions-page.component';
import { PlanResolverService } from './plans/components/plan-resolver.service';
import { ReadingsUploadPageComponent } from './readings/components/readings-upload-page/readings-upload-page.component';
import { AuthenticationGuard } from './core/authentication.guard';
import { CanDeactivateGuard } from './core/can-deactivate.guard';
import { ReadingsUploadsHistoryPageComponent } from './readings/components/readings-uploads-history-page/readings-uploads-history-page.component';
import { MetersPageComponent } from './meters/components/meters-page/meters-page.component';
import { ClientsPageComponent } from './clients/components/clients-page/clients-page.component';
import { ClientPlanAssignmentsPageComponent } from './clients/components/client-plan-assignments-page/client-plan-assignments-page.component';
import { ClientResolverService } from './clients/components/client-resolver.service';

const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  { path: 'dashboard', component: DashboardPageComponent, canActivate: [AuthenticationGuard] },
  { path: 'invoiceRuns', component: InvoiceRunsPageComponent, canActivate: [AuthenticationGuard] },
  { path: 'invoices', component: InvoicesPageComponent, canActivate: [AuthenticationGuard] },
  {
    path: 'plans/:id/versions', component: PlanVersionsPageComponent, canActivate: [AuthenticationGuard], resolve: {
      plan: PlanResolverService
    }
  },
  { path: 'plans', component: PlansPageComponent, canActivate: [AuthenticationGuard] },
  { path: 'readingsUpload', component: ReadingsUploadPageComponent, canActivate: [AuthenticationGuard], canDeactivate: [CanDeactivateGuard] },
  { path: 'readingsUploadsHistory', component: ReadingsUploadsHistoryPageComponent, canActivate: [AuthenticationGuard] },
  { path: 'meters', component: MetersPageComponent, canActivate: [AuthenticationGuard] },
  {
    path: 'clients/:id/planAssignments', component: ClientPlanAssignmentsPageComponent, canActivate: [AuthenticationGuard], resolve: {
      client: ClientResolverService
    }
  },
  { path: 'clients', component: ClientsPageComponent, canActivate: [AuthenticationGuard] },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
