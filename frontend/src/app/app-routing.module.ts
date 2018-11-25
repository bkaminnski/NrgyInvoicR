import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard/components/dashboard/dashboard.component';
import { InvoicesPageComponent } from './invoices/components/invoices-page/invoices-page.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'invoices', component: InvoicesPageComponent },
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
