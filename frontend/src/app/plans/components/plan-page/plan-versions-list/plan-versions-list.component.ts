import { Location } from '@angular/common';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatSort, MatPaginator } from '@angular/material';
import { Plan } from 'src/app/plans/model/plan.model';
import { PlanVersionsListService } from './plan-versions-list.service';
import { PlanVersionsListDataSource } from './plan-versions-list.datasource';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Component({
  selector: 'app-plan-versions-list',
  templateUrl: './plan-versions-list.component.html',
  styleUrls: ['./plan-versions-list.component.scss']
})
export class PlanVersionsListComponent implements OnInit, AfterViewInit {
  public highlightedRowIndex: number;
  public dataSource: PlanVersionsListDataSource;
  public displayedColumns: string[] = ['validSince', 'fixedFees.subscriptionFee', 'fixedFees.networkFee', 'description', 'options'];
  public plan: Plan;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  sortConfig = {
    initialSortActive: 'validSince',
    initialSortDirection: 'desc'
  };

  paginatorConfig = {
    initialPageIndex: 0,
    initialPageSize: 10,
    pageSizeOptions: [10, 100, 1000]
  };

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private planVersionsListService: PlanVersionsListService
  ) {
    this.dataSource = new PlanVersionsListDataSource(this.planVersionsListService);
  }

  ngOnInit() {
    this.route.data.subscribe((data: { plan: Plan }) => { this.plan = data.plan; setTimeout(() => this.search()); });
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.resetPaginatorAndSearchWithCriteria());
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search() {
    this.resetPaginatorAndSearchWithCriteria();
  }

  private resetPaginatorAndSearchWithCriteria() {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria();
  }

  private searchWithCriteria() {
    this.dataSource.loadPlanVersions(this.plan, PageDefinition.forSortAndPaginator(this.sort, this.paginator));
  }

  mouseEnter(highlightedRowIndex: number) {
    this.highlightedRowIndex = highlightedRowIndex;
  }

  mouseLeave() {
    this.highlightedRowIndex = null;
  }

  back() {
    this.location.back();
  }
}
