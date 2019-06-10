import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort, MatPaginator, MatDialog, MatDialogConfig } from '@angular/material';
import { MetersService } from '../meters.service';
import { MetersListDataSource } from './meters-list.datasource';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';
import { Meter } from 'src/app/meters/model/meter.model';
import { MeterDialogComponent } from '../meter-dialog/meter-dialog.component';

@Component({
  selector: 'app-meters-list',
  templateUrl: './meters-list.component.html',
  styleUrls: ['./meters-list.component.scss']
})
export class MetersListComponent implements OnInit, AfterViewInit {
  private metersSearchCriteria: MetersSearchCriteria;
  dataSource: MetersListDataSource;
  displayedColumns: string[] = ['serialNumber', 'createdDate'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  sortConfig = {
    initialSortActive: 'createdDate',
    initialSortDirection: 'desc'
  };

  paginatorConfig = {
    initialPageIndex: 0,
    initialPageSize: 10,
    pageSizeOptions: [10, 100, 1000]
  };

  constructor(private metersService: MetersService, private dialog: MatDialog) {
    this.dataSource = new MetersListDataSource(this.metersService);
    this.metersSearchCriteria = new MetersSearchCriteria();
  }

  ngOnInit() { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.resetPaginatorAndSearchWithCriteria());
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search(metersSearchCriteria: MetersSearchCriteria) {
    this.metersSearchCriteria = metersSearchCriteria;
    this.resetPaginatorAndSearchWithCriteria();
  }

  private resetPaginatorAndSearchWithCriteria() {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria();
  }

  private searchWithCriteria() {
    this.dataSource.loadMeters(
      this.metersSearchCriteria,
      (this.sort.active) ? this.sort.active : this.sortConfig.initialSortActive,
      (this.sort.direction) ? this.sort.direction : this.sortConfig.initialSortDirection,
      (this.paginator.pageIndex) ? this.paginator.pageIndex : this.paginatorConfig.initialPageIndex,
      (this.paginator.pageSize) ? this.paginator.pageSize : this.paginatorConfig.initialPageSize
    );
  }

  registerMeter() {
    const dialogConfig: MatDialogConfig<Meter> = {
      data: new Meter(this.dataSource.totalElements === 0 ? this.metersSearchCriteria.serialNumber : ''),
      disableClose: true,
      autoFocus: true,
      minWidth: '33%'
    };

    this.dialog.open(MeterDialogComponent, dialogConfig)
      .afterClosed()
      .subscribe(meter => {
        if (meter) {
          this.metersSearchCriteria.serialNumber = meter.serialNumber;
          this.resetPaginatorAndSearchWithCriteria();
        }
      });
  }
}
