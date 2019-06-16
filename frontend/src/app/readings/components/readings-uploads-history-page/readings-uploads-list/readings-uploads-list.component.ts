import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { ReadingsUploadsSearchCriteria } from 'src/app/readings/model/readings-uploads-search-criteria.model';
import { ReadingsUploadsListDataSource } from './readings-uploads-list.datasource';
import { ReadingsUploadsListService } from './readings-uploads-list.service';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Component({
  selector: 'app-readings-uploads-list',
  templateUrl: './readings-uploads-list.component.html',
  styleUrls: ['./readings-uploads-list.component.scss']
})
export class ReadingsUploadsListComponent implements OnInit, AfterViewInit {
  private readingsUploadsSearchCriteria: ReadingsUploadsSearchCriteria;
  dataSource: ReadingsUploadsListDataSource;
  displayedColumns: string[] = [
    'date',
    'fileName',
    'status',
    'errorMessage',
    'reading.readingSpread.numberOfMeasuredValues',
    'reading.readingSpread.numberOfExpectedValues'
  ];

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
    this.sort.sortChange.subscribe(() => this.resetPaginatorAndSearchWithCriteria());
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search(readingsUploadsSearchCriteria: ReadingsUploadsSearchCriteria) {
    this.readingsUploadsSearchCriteria = readingsUploadsSearchCriteria;
    this.resetPaginatorAndSearchWithCriteria();
  }

  private resetPaginatorAndSearchWithCriteria() {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria();
  }

  private searchWithCriteria() {
    this.dataSource.loadReadingsUploads(this.readingsUploadsSearchCriteria, PageDefinition.forSortAndPaginator(this.sort, this.paginator));
  }
}
