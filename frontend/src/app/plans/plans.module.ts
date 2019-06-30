import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { MaterialModule } from '../core/material.module';

import { PlansPageComponent } from './components/plans-page/plans-page.component';
import { PlansListComponent } from './components/plans-page/plans-list/plans-list.component';
import { CoreModule } from '../core/core.module';
import { PlanVersionsListComponent } from './components/plan-page/plan-versions-list/plan-versions-list.component';
import { PlanPageComponent } from './components/plan-page/plan-page.component';

@NgModule({
  declarations: [
    PlansPageComponent,
    PlansListComponent,
    PlanVersionsListComponent,
    PlanPageComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    CoreModule
  ]
})
export class PlansModule { }
