import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { CoreModule } from '../core/core.module';
import { PlansPageComponent } from './components/plans-page/plans-page.component';
import { PlansListComponent } from './components/plans-page/plans-list/plans-list.component';
import { PlanVersionsPageComponent } from './components/plan-versions-page/plan-versions-page.component';
import { PlanVersionsListComponent } from './components/plan-versions-page/plan-versions-list/plan-versions-list.component';
import { PlanVersionDialogComponent } from './components/plan-versions-page/plan-version-dialog/plan-version-dialog.component';
import { FlattenedBucketsListComponent } from './components/plan-versions-page/flattened-buckets-list/flattened-buckets-list.component';
import { ExpressionTestResultValidatorDirective } from './components/plan-versions-page/plan-version-dialog/expression-test-result.directive';

@NgModule({
  declarations: [
    PlansPageComponent,
    PlansListComponent,
    PlanVersionsPageComponent,
    PlanVersionsListComponent,
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
