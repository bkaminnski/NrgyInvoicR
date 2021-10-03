import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort, MatPaginator, MatDialog, MatDialogConfig } from '@angular/material';
import { MetersService } from '../../meters.service';
import { MetersListDataSource } from './meters-list.datasource';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';
import { Meter } from 'src/app/meters/model/meter.model';
import { MeterDialogComponent } from '../meter-dialog/meter-dialog.component';
import { Page } from 'src/app/core/model/page.model';
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
  public spotlightedRowIndex: number;
  public dataSource: MetersListDataSource;
  public displayedColumns: string[] = ['serialNumber', 'client.number', 'createdDate', 'options'];

  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

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

  private resetPaginatorAndSearchWithCriteria(meter: Meter = null) {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria(meter);
  }

  private searchWithCriteria(meter: Meter = null) {
    this.dataSource.loadMeters(
      this.metersSearchCriteria,
      PageDefinition.forSortAndPaginator(this.sort, this.paginator),
      (page: Page<Meter>) => this.spotlightMeterOnPage(page, meter)
    );
  }

  private spotlightMeterOnPage(page: Page<Meter>, meter: Meter) {
    if (meter !== null) {
      this.spotlightedRowIndex = page.indexOf(meter);
      setTimeout(() => this.spotlightedRowIndex = null, 1000);
    }
  }

  registerMeter() {
    const meter = new Meter(this.dataSource.totalElements === 0 ? this.metersSearchCriteria.serialNumber : '');
    this.openMeterDialog(meter).subscribe(m => {
      if (m) {
        this.metersSearchCriteria.reset();
        this.resetPaginatorAndSearchWithCriteria(m);
      }
    });
  }

  editMeter(meter: Meter) {
    this.openMeterDialog(meter).subscribe(m => {
      if (m) {
        this.searchWithCriteria(m);
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
