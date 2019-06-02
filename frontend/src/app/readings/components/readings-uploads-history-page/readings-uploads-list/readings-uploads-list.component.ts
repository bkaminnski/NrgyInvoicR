import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { ReadingsUploadsSearchCriteria } from 'src/app/readings/model/readings-uploads-search-criteria.model';
import { ReadingsUploadsListDataSource } from './readings-uploads-list.datasource';
import { ReadingsUploadsListService } from './readings-uploads-list.service';

@Component({
  selector: 'app-readings-uploads-list',
  templateUrl: './readings-uploads-list.component.html',
  styleUrls: ['./readings-uploads-list.component.scss']
})
export class ReadingsUploadsListComponent implements OnInit, AfterViewInit {
  private readingsUploadsSearchCriteria: ReadingsUploadsSearchCriteria;
  dataSource: ReadingsUploadsListDataSource;
  displayedColumns: string[] = ['date', 'fileName', 'status', 'errorMessage'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  sortConfig = {
    initialSortActive: 'date',
    initialSortDirection: 'desc'
  };

  paginatorConfig = {
    initialPageIndex: 0,
    initialPageSize: 10,
    pageSizeOptions: [10, 100, 1000]
  };

  constructor(private readingsUploadsListService: ReadingsUploadsListService) {
    this.dataSource = new ReadingsUploadsListDataSource(this.readingsUploadsListService);
  }

  ngOnInit() { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => { this.paginator.pageIndex = 0; this.searchWithCriteria(); });
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search(readingsUploadsSearchCriteria: ReadingsUploadsSearchCriteria) {
    this.readingsUploadsSearchCriteria = readingsUploadsSearchCriteria;
    this.searchWithCriteria();
  }

  private searchWithCriteria() {
    this.dataSource.loadReadingsUploads(
      this.readingsUploadsSearchCriteria,
      (this.sort.active) ? this.sort.active : this.sortConfig.initialSortActive,
      (this.sort.direction) ? this.sort.direction : this.sortConfig.initialSortDirection,
      (this.paginator.pageIndex) ? this.paginator.pageIndex : this.paginatorConfig.initialPageIndex,
      (this.paginator.pageSize) ? this.paginator.pageSize : this.paginatorConfig.initialPageSize
    );
  }
}
