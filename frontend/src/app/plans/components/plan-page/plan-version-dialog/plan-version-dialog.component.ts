import { Component, OnInit, Inject } from '@angular/core';
import { PlanVersion } from 'src/app/plans/model/plan-version.model';
import { ExpressionTestResult } from 'src/app/plans/model/expression-test-result.model';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Plan } from 'src/app/plans/model/plan.model';
import { PlanVersionsService } from '../plan-versions.service';
import { NotificationService } from 'src/app/core/components/notification/notification.service';
import { finalize } from 'rxjs/operators';

@Component({
  templateUrl: './plan-version-dialog.component.html',
  styleUrls: ['./plan-version-dialog.component.scss'],
  providers: [
    PlanVersionsService
  ]
})
export class PlanVersionDialogComponent implements OnInit {
  public loading: boolean;
  private plan: Plan;
  public planVersion: PlanVersion;
  public expressionTestResult: ExpressionTestResult;
  constructor(
    private dialogRef: MatDialogRef<PlanVersionDialogComponent>,
    private planVersionsService: PlanVersionsService,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) data: { plan: Plan, planVersion: PlanVersion }
  ) {
    this.plan = data.plan;
    this.planVersion = PlanVersion.cloned(data.planVersion);
    this.loading = false;
  }
  ngOnInit() { }

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
