import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Meter } from 'src/app/meters/model/meter.model';
import { MetersService } from '../meters.service';
import { finalize } from 'rxjs/operators';
import { NotificationService } from 'src/app/core/components/notification/notification.service';

@Component({
  selector: 'app-meter-dialog',
  templateUrl: './meter-dialog.component.html',
  styleUrls: ['./meter-dialog.component.scss'],
  providers: [
    MetersService
  ]
})
export class MeterDialogComponent implements OnInit {
  private newMeter: boolean;
  public meter: Meter;
  public loading: boolean;

  constructor(
    private dialogRef: MatDialogRef<MeterDialogComponent>,
    private metersService: MetersService,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) meter: Meter
  ) {
    this.meter = Meter.cloned(meter);
    this.newMeter = (this.meter.id === null);
    this.loading = false;
  }

  ngOnInit() {
  }

  save() {
    this.loading = true;
    this.metersService.saveMeter(this.meter)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe(
        meter => this.handleSuccess(meter),
        errorResponse => this.handleError(errorResponse)
      );
  }

  private handleSuccess(meter: Meter): void {
    this.notificationService.success(this.newMeter ? 'A new meter has been successfully registered.' : 'A meter has been successfully updated.');
    return this.dialogRef.close(meter);
  }

  private handleError(errorResponse: any): void {
    return this.notificationService.error(errorResponse.error.errorMessage);
  }

  cancel() {
    this.dialogRef.close(null);
  }
}
