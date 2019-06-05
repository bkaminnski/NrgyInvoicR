import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort, MatPaginator } from '@angular/material';
import { MetersListService } from './meters-list.service';
import { MetersListDataSource } from './meters-list.datasource';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';

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

  constructor(private metersListService: MetersListService) {
    this.dataSource = new MetersListDataSource(this.metersListService);
  }

  ngOnInit() { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => { this.paginator.pageIndex = 0; this.searchWithCriteria(); });
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search(metersSearchCriteria: MetersSearchCriteria) {
    this.metersSearchCriteria = metersSearchCriteria;
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
}
