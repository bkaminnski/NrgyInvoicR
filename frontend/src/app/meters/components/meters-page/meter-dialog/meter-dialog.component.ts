import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Meter } from 'src/app/meters/model/meter.model';
import { MetersService } from '../meters.service';
import { catchError, finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-meter-dialog',
  templateUrl: './meter-dialog.component.html',
  styleUrls: ['./meter-dialog.component.scss'],
  providers: [
    MetersService
  ]
})
export class MeterDialogComponent implements OnInit {
  public meter: Meter;
  public loading: boolean;

  constructor(
    private dialogRef: MatDialogRef<MeterDialogComponent>,
    private metersService: MetersService,
    private snackBar: MatSnackBar
  ) {
    this.meter = new Meter();
    this.loading = false;
  }

  ngOnInit() {
  }

  save() {
    this.loading = true;
    this.metersService.createMeter(this.meter)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe(
        meter => this.dialogRef.close(meter),
        errorResponse => this.snackBar.open(errorResponse.error.errorMessage)
      );
  }

  cancel() {
    this.dialogRef.close(null);
  }
}
