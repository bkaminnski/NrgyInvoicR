import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MatDialogConfig, MatDialog } from '@angular/material';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';
import { MeterDialogComponent } from '../meter-dialog/meter-dialog.component';
import { tap } from 'rxjs/operators';
import { Meter } from 'src/app/meters/model/meter.model';

@Component({
  selector: 'app-meters-filter',
  templateUrl: './meters-filter.component.html',
  styleUrls: ['./meters-filter.component.scss']
})
export class MetersFilterComponent implements OnInit {
  @Output() searchEvent = new EventEmitter();
  metersSearchCriteria: MetersSearchCriteria;

  constructor(private dialog: MatDialog) {
    this.metersSearchCriteria = new MetersSearchCriteria('');
  }

  ngOnInit() { }

  search() {
    this.searchEvent.emit(this.metersSearchCriteria);
  }

  registerMeter() {
    this.dialog.open(MeterDialogComponent, { disableClose: true, autoFocus: true, minWidth: '33%' })
      .afterClosed()
      .subscribe(meter => {
        if (meter) {
          this.metersSearchCriteria.serialNumber = meter.serialNumber;
          this.search();
        }
      });
  }
}
