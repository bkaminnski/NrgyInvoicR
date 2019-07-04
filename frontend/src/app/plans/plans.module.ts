import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { PlansPageComponent } from './components/plans-page/plans-page.component';
import { PlansListComponent } from './components/plans-page/plans-list/plans-list.component';
import { CoreModule } from '../core/core.module';
import { PlanVersionsListComponent } from './components/plan-page/plan-versions-list/plan-versions-list.component';
import { PlanPageComponent } from './components/plan-page/plan-page.component';
import { PlanVersionDialogComponent } from './components/plan-page/plan-version-dialog/plan-version-dialog.component';
import { FlattenedBucketsListComponent } from './components/plan-page/flattened-buckets-list/flattened-buckets-list.component';
import { ExpressionTestResultValidatorDirective } from './components/plan-page/plan-version-dialog/expression-test-result.directive';

@NgModule({
  declarations: [
    PlansPageComponent,
    PlansListComponent,
    PlanVersionsListComponent,
    PlanPageComponent,
    PlanVersionDialogComponent,
    FlattenedBucketsListComponent,
    ExpressionTestResultValidatorDirective
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    CoreModule,
    FormsModule
  ]
})
export class PlansModule { }
