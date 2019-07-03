import { Location } from '@angular/common';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatSort, MatPaginator, MatDialog, MatDialogConfig } from '@angular/material';
import { Plan } from 'src/app/plans/model/plan.model';
import { PlanVersionsService } from '../plan-versions.service';
import { PlanVersionsListDataSource } from './plan-versions-list.datasource';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { PlanVersionDialogComponent } from '../plan-version-dialog/plan-version-dialog.component';
import { PlanVersion } from 'src/app/plans/model/plan-version.model';
import { Page } from 'src/app/core/model/page.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-plan-versions-list',
  templateUrl: './plan-versions-list.component.html',
  styleUrls: ['./plan-versions-list.component.scss']
})
export class PlanVersionsListComponent implements OnInit, AfterViewInit {
  public highlightedRowIndex: number;
  public spotlightedRowIndex: number;
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
    private planVersionsService: PlanVersionsService,
    private dialog: MatDialog
  ) {
    this.dataSource = new PlanVersionsListDataSource(this.planVersionsService);
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

  private resetPaginatorAndSearchWithCriteria(planVersion: PlanVersion = null) {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria(planVersion);
  }

  private searchWithCriteria(planVersion: PlanVersion = null) {
    this.dataSource.loadPlanVersions(
      this.plan,
      PageDefinition.forSortAndPaginator(this.sort, this.paginator),
      (page: Page<PlanVersion>) => this.spotlightClientOnPage(page, planVersion)
    );
  }

  private spotlightClientOnPage(page: Page<PlanVersion>, planVersion: PlanVersion) {
    if (planVersion !== null) {
      this.spotlightedRowIndex = page.indexOf(planVersion);
      setTimeout(() => this.spotlightedRowIndex = null, 1000);
    }
  }

  registerPlanVersion() {
    const planVersion = new PlanVersion(null);
    this.openPlanVersionDialog(planVersion).subscribe(d => {
      if (d) {
        this.resetPaginatorAndSearchWithCriteria(d);
      }
    });
  }

  editPlanVersion(planVersion: PlanVersion) {
    this.openPlanVersionDialog(planVersion).subscribe(d => {
      if (d) {
        this.searchWithCriteria(d);
      }
    });
  }

  openPlanVersionDialog(planVersion: PlanVersion): Observable<PlanVersion> {
    const dialogConfig: MatDialogConfig<{ plan: Plan, planVersion: PlanVersion }> = {
      data: { plan: this.plan, planVersion: planVersion },
      disableClose: true,
      autoFocus: true,
      minWidth: '50%',
      maxWidth: '70%'
    };
    return this.dialog.open(PlanVersionDialogComponent, dialogConfig).afterClosed();
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
