import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { Meter } from 'src/app/meters/model/meter.model';

@Component({
  selector: 'app-meter-dialog',
  templateUrl: './meter-dialog.component.html',
  styleUrls: ['./meter-dialog.component.scss']
})
export class MeterDialogComponent implements OnInit {
  public meter: Meter;

  constructor(private dialogRef: MatDialogRef<MeterDialogComponent>) {
    this.meter = new Meter();
  }

  ngOnInit() {
  }

  save() {
    this.dialogRef.close(this.meter);
  }

  cancel() {
    this.dialogRef.close(null);
  }
}
