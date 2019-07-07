import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSort, MatPaginator, MatDialog, MatDialogConfig } from '@angular/material';
import { Client } from 'src/app/clients/model/client.model';
import { ClientPlanAssignment } from 'src/app/clients/model/client-plan-assignment.model';
import { ClientPlanAssignmentsService } from '../client-plan-assignments.service';
import { ClientPlanAssignmentsListDataSource } from './client-plan-assignments-list.datasource';
import { ClientPlanAssignmentDialogComponent } from '../client-plan-assignment-dialog/client-plan-assignment-dialog.component';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Component({
  selector: 'app-client-plan-assignments-list',
  templateUrl: './client-plan-assignments-list.component.html',
  styleUrls: ['./client-plan-assignments-list.component.scss']
})
export class ClientPlanAssignmentsListComponent implements OnInit, AfterViewInit {
  public highlightedRowIndex: number;
  public spotlightedRowIndex: number;
  public dataSource: ClientPlanAssignmentsListDataSource;
  public displayedColumns: string[] = ['validSince', 'plan.name', 'options'];
  public client: Client;

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
    private router: Router,
    private clientPlanAssignmentsService: ClientPlanAssignmentsService,
    private dialog: MatDialog
  ) {
    this.dataSource = new ClientPlanAssignmentsListDataSource(this.clientPlanAssignmentsService);
  }

  ngOnInit() {
    this.route.data.subscribe((data: { client: Client }) => { this.client = data.client; setTimeout(() => this.search()); });
  }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.resetPaginatorAndSearchWithCriteria());
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search() {
    this.resetPaginatorAndSearchWithCriteria();
  }

  private resetPaginatorAndSearchWithCriteria(clientPlanAssignment: ClientPlanAssignment = null) {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria(clientPlanAssignment);
  }

  private searchWithCriteria(clientPlanAssignment: ClientPlanAssignment = null) {
    this.dataSource.loadClientPlanAssignments(
      this.client,
      PageDefinition.forSortAndPaginator(this.sort, this.paginator),
      (page: Page<ClientPlanAssignment>) => this.spotlightClientPlanAssignmentOnPage(page, clientPlanAssignment)
    );
  }

  private spotlightClientPlanAssignmentOnPage(page: Page<ClientPlanAssignment>, clientPlanAssignment: ClientPlanAssignment) {
    if (clientPlanAssignment !== null) {
      this.spotlightedRowIndex = page.indexOf(clientPlanAssignment);
      setTimeout(() => this.spotlightedRowIndex = null, 1000);
    }
  }

  assignPlan() {
    const clientPlanAssignment = new ClientPlanAssignment(null);
    this.openClientPlanAssignmentDialog(clientPlanAssignment).subscribe(d => {
      if (d) {
        this.resetPaginatorAndSearchWithCriteria(d);
      }
    });
  }

  editClientPlanAssignment(clientPlanAssignment: ClientPlanAssignment) {
    this.openClientPlanAssignmentDialog(clientPlanAssignment).subscribe(d => {
      if (d) {
        this.searchWithCriteria(d);
      }
    });
  }

  openClientPlanAssignmentDialog(clientPlanAssignment: ClientPlanAssignment): Observable<ClientPlanAssignment> {
    const dialogConfig: MatDialogConfig<{ client: Client, clientPlanAssignment: ClientPlanAssignment }> = {
      data: { client: this.client, clientPlanAssignment: clientPlanAssignment },
      disableClose: true,
      autoFocus: true,
      minWidth: '33%'
    };
    return this.dialog.open(ClientPlanAssignmentDialogComponent, dialogConfig).afterClosed();
  }

  mouseEnter(highlightedRowIndex: number) {
    this.highlightedRowIndex = highlightedRowIndex;
  }

  mouseLeave() {
    this.highlightedRowIndex = null;
  }

  back() {
    this.router.navigate(['/clients']);
  }
}
