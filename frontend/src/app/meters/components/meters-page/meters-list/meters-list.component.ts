import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort, MatPaginator, MatDialog, MatDialogConfig } from '@angular/material';
import { MetersService } from '../meters.service';
import { MetersListDataSource } from './meters-list.datasource';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';
import { Meter } from 'src/app/meters/model/meter.model';
import { MeterDialogComponent } from '../meter-dialog/meter-dialog.component';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-meters-list',
  templateUrl: './meters-list.component.html',
  styleUrls: ['./meters-list.component.scss']
})
export class MetersListComponent implements OnInit, AfterViewInit {
  private metersSearchCriteria: MetersSearchCriteria;
  public highlightedRowIndex: number;
  public dataSource: MetersListDataSource;
  public displayedColumns: string[] = ['serialNumber', 'createdDate', 'options'];

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
    this.dataSource.loadMeters(this.metersSearchCriteria, new PageDefinition(this.sort, this.paginator));
  }

  registerMeter() {
    const meter = new Meter(this.dataSource.totalElements === 0 ? this.metersSearchCriteria.serialNumber : '');
    this.openMeterDialog(meter).subscribe(m => {
      if (m) {
        this.metersSearchCriteria.reset();
        this.resetPaginatorAndSearchWithCriteria();
      }
    });
  }

  editMeter(meter: Meter) {
    this.openMeterDialog(meter).subscribe(m => {
      if (m) {
        this.searchWithCriteria();
      }
    });
  }

  openMeterDialog(meter: Meter): Observable<Meter> {
    const dialogConfig: MatDialogConfig<Meter> = {
      data: meter,
      disableClose: true,
      autoFocus: true,
      minWidth: '33%'
    };
    return this.dialog.open(MeterDialogComponent, dialogConfig).afterClosed();
  }

  mouseEnter(highlightedRowIndex: number) {
    this.highlightedRowIndex = highlightedRowIndex;
  }

  mouseLeave() {
    this.highlightedRowIndex = null;
  }
}
