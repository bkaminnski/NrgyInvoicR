import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { MarketingNamesListService } from './marketing-names-list.service';
import { MarketingNamesListDataSource } from './marketing-names-list.datasource';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Component({
  selector: 'app-marketing-names-list',
  templateUrl: './marketing-names-list.component.html',
  styleUrls: ['./marketing-names-list.component.scss']
})
export class MarketingNamesListComponent implements OnInit, AfterViewInit {
  dataSource: MarketingNamesListDataSource;
  displayedColumns: string[] = ['name', 'description'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  sortConfig = {
    initialSortActive: 'name',
    initialSortDirection: 'asc'
  };

  paginatorConfig = {
    initialPageIndex: 0,
    initialPageSize: 10,
    pageSizeOptions: [10, 100, 1000]
  };

  constructor(private marketingNamesListService: MarketingNamesListService) {
    this.dataSource = new MarketingNamesListDataSource(this.marketingNamesListService);
  }

  ngOnInit() { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.resetPaginatorAndSearchWithCriteria());
    this.paginator.page.subscribe(() => this.searchWithCriteria());
    setTimeout(() => this.search());
  }

  search() {
    this.resetPaginatorAndSearchWithCriteria();
  }

  private resetPaginatorAndSearchWithCriteria() {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria();
  }

  private searchWithCriteria() {
    this.dataSource.loadMarketingNames(PageDefinition.forSortAndPaginator(this.sort, this.paginator));
  }
}
