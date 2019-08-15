import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort, MatPaginator, MatDialog, MatDialogConfig } from '@angular/material';
import { InvoiceRunsService } from '../invoice-runs.service';
import { InvoiceRunsListDataSource } from './invoice-runs-list.datasource';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { InvoiceRunDialogComponent } from '../invoice-run-dialog/invoice-run-dialog.component';
import { InvoiceRun } from 'src/app/invoices/model/invoice-run.model';
import { Page } from 'src/app/core/model/page.model';
import { Observable } from 'rxjs';
import { NotificationService } from 'src/app/core/components/notification/notification.service';

@Component({
  selector: 'app-invoice-runs-list',
  templateUrl: './invoice-runs-list.component.html',
  styleUrls: ['./invoice-runs-list.component.scss']
})
export class InvoiceRunsListComponent implements OnInit, AfterViewInit {
  public highlightedRowIndex: number;
  public spotlightedRowIndex: number;
  public dataSource: InvoiceRunsListDataSource;
  public displayedColumns: string[] = [
    'issueDate', 'sinceClosed', 'untilOpen', 'firstInvoiceNumber', 'numberTemplate',
    'status', 'progress.numberOfInvoicesToGenerate', 'progress.numberOfSuccesses', 'progress.numberOfFailures',
    'options'
  ];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  sortConfig = {
    initialSortActive: 'issueDate',
    initialSortDirection: 'desc'
  };

  paginatorConfig = {
    initialPageIndex: 0,
    initialPageSize: 10,
    pageSizeOptions: [10, 100, 1000]
  };

  constructor(
    private invoiceRunsService: InvoiceRunsService,
    private dialog: MatDialog,
    private notificationService: NotificationService
  ) {
    this.dataSource = new InvoiceRunsListDataSource(this.invoiceRunsService);
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

  private resetPaginatorAndSearchWithCriteria(invoiceRun: InvoiceRun = null) {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria(invoiceRun);
  }

  private searchWithCriteria(invoiceRun: InvoiceRun = null) {
    this.dataSource.loadInvoiceRuns(
      PageDefinition.forSortAndPaginator(this.sort, this.paginator),
      (page: Page<InvoiceRun>) => this.spotlightClientOnPage(page, invoiceRun)
    );
  }

  private spotlightClientOnPage(page: Page<InvoiceRun>, invoiceRun: InvoiceRun) {
    if (invoiceRun !== null) {
      this.spotlightedRowIndex = page.indexOf(invoiceRun);
      setTimeout(() => this.spotlightedRowIndex = null, 1000);
    }
  }

  createInvoiceRun() {
    this.invoiceRunsService.prepareNewInvoiceRun().subscribe(newInvoiceRun => {
      this.openInvoiceRunDialog(newInvoiceRun).subscribe(i => {
        if (i) {
          this.resetPaginatorAndSearchWithCriteria(i);
        }
      });
    });
  }

  startInvoiceRun(invoiceRun: InvoiceRun) {
    this.invoiceRunsService.startInvoiceRun(invoiceRun)
      .subscribe(
        i => this.dataSource.autoRefresh(i),
        errorResponse => this.handleError(errorResponse)
      );
  }

  private handleError(errorResponse: any): void {
    return this.notificationService.error(errorResponse.error.errorMessage);
  }

  openInvoiceRunDialog(invoiceRun: InvoiceRun): Observable<InvoiceRun> {
    const dialogConfig: MatDialogConfig<{ invoiceRun: InvoiceRun }> = {
      data: { invoiceRun: invoiceRun },
      disableClose: true,
      autoFocus: true,
      minWidth: '50%',
      maxWidth: '70%',
      position: { top: '100px' }
    };
    return this.dialog.open(InvoiceRunDialogComponent, dialogConfig).afterClosed();
  }

  mouseEnter(highlightedRowIndex: number) {
    this.highlightedRowIndex = highlightedRowIndex;
  }

  mouseLeave() {
    this.highlightedRowIndex = null;
  }
}
