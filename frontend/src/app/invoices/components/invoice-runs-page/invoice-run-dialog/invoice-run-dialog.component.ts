import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, ShowOnDirtyErrorStateMatcher } from '@angular/material';
import { finalize } from 'rxjs/operators';
import { InvoiceRun } from 'src/app/invoices/model/invoice-run.model';
import { InvoiceRunsService } from '../invoice-runs.service';
import { NotificationService } from 'src/app/core/components/notification/notification.service';

@Component({
  templateUrl: './invoice-run-dialog.component.html',
  styleUrls: ['./invoice-run-dialog.component.scss'],
  providers: [
    InvoiceRunsService
  ]
})
export class InvoiceRunDialogComponent implements OnInit {
  public loading: boolean;
  public invoiceRun: InvoiceRun;
  public errorStateMatcher = new ShowOnDirtyErrorStateMatcher();

  constructor(
    private dialogRef: MatDialogRef<InvoiceRunDialogComponent>,
    private invoiceRunsService: InvoiceRunsService,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) data: { invoiceRun: InvoiceRun }
  ) {
    this.invoiceRun = InvoiceRun.cloned(data.invoiceRun);
    this.loading = false;
  }

  ngOnInit() { }

  save() {
    this.loading = true;
    this.invoiceRunsService.saveInvoiceRun(this.invoiceRun)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe(
        invoiceRun => this.handleSuccess(invoiceRun),
        errorResponse => this.handleError(errorResponse)
      );
  }

  private handleSuccess(invoiceRun: InvoiceRun): void {
    this.notificationService.success(this.invoiceRun.isNew() ? 'A new invoice run has been successfully created.' : 'An invoice plan has been successfully updated.');
    return this.dialogRef.close(invoiceRun);
  }

  private handleError(errorResponse: any): void {
    return this.notificationService.error(errorResponse.error.errorMessage);
  }

  cancel() {
    this.dialogRef.close(null);
  }
}
