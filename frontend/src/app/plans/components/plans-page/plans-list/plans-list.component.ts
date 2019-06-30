import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { PlansListService } from './plans-list.service';
import { PlansListDataSource } from './plans-list.datasource';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Component({
  selector: 'app-plans-list',
  templateUrl: './plans-list.component.html',
  styleUrls: ['./plans-list.component.scss']
})
export class PlansListComponent implements OnInit, AfterViewInit {
  dataSource: PlansListDataSource;
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

  constructor(private plansListService: PlansListService) {
    this.dataSource = new PlansListDataSource(this.plansListService);
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
    this.dataSource.loadPlans(PageDefinition.forSortAndPaginator(this.sort, this.paginator));
  }
}
