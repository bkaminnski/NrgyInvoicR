import { Component, OnInit, Inject, OnDestroy, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, ShowOnDirtyErrorStateMatcher } from '@angular/material';
import { Observable, Subject, Subscription } from 'rxjs';
import { finalize, debounceTime } from 'rxjs/operators';
import { PlanVersion } from 'src/app/plans/model/plan-version.model';
import { Plan } from 'src/app/plans/model/plan.model';
import { PlanVersionsService } from '../plan-versions.service';
import { NotificationService } from 'src/app/core/components/notification/notification.service';
import { ExpressionService } from './expression.service';
import { FlattenedBucket } from 'src/app/plans/model/flattened-bucket.model';
import { NgModel } from '@angular/forms';

@Component({
  templateUrl: './plan-version-dialog.component.html',
  styleUrls: ['./plan-version-dialog.component.scss'],
  providers: [
    PlanVersionsService,
    ExpressionService
  ]
})
export class PlanVersionDialogComponent implements OnInit, OnDestroy {
  public loading: boolean;
  private plan: Plan;
  public planVersion: PlanVersion;
  private expressionSubject = new Subject<string>();
  private expressionSubscription: Subscription;
  private flattenedBucketsSubject: Subject<FlattenedBucket[]>;
  public flattenedBucketsObservable: Observable<FlattenedBucket[]>;
  public errorStateMatcher = new ShowOnDirtyErrorStateMatcher();

  constructor(
    private dialogRef: MatDialogRef<PlanVersionDialogComponent>,
    private planVersionsService: PlanVersionsService,
    private notificationService: NotificationService,
    private expressionService: ExpressionService,
    @Inject(MAT_DIALOG_DATA) data: { plan: Plan, planVersion: PlanVersion }
  ) {
    this.plan = data.plan;
    this.planVersion = PlanVersion.cloned(data.planVersion);
    this.loading = false;
    this.expressionSubscription = this.expressionSubject
      .asObservable()
      .pipe(
        debounceTime(300)
      )
      .subscribe(e => this.testExpression());
    this.flattenedBucketsSubject = new Subject();
    this.flattenedBucketsObservable = this.flattenedBucketsSubject.asObservable();
  }

  ngOnInit() {
    this.expressionSubject.next(this.planVersion.expression);
  }

  ngOnDestroy(): void {
    this.flattenedBucketsSubject.complete();
    this.expressionSubscription.unsubscribe();
  }

  expressionChanged(expression: string) {
    this.expressionSubject.next(expression);
  }

  private testExpression() {
    this.expressionService
      .testExpression(this.planVersion.expression)
      .subscribe(e => this.flattenedBucketsSubject.next(e.flattenedBuckets));
  }

  save() {
    this.loading = true;
    this.planVersionsService.savePlanVersion(this.plan, this.planVersion)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe(
        planVersion => this.handleSuccess(planVersion),
        errorResponse => this.handleError(errorResponse)
      );
  }

  private handleSuccess(planVersion: PlanVersion): void {
    this.notificationService.success(this.planVersion.isNew() ? 'A new plan version has been successfully registered.' : 'A plan version has been successfully updated.');
    return this.dialogRef.close(planVersion);
  }

  private handleError(errorResponse: any): void {
    return this.notificationService.error(errorResponse.error.errorMessage);
  }

  cancel() {
    this.dialogRef.close(null);
  }
}
