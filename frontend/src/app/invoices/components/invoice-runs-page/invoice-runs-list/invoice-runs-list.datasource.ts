import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of, Subscription, interval } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { InvoiceRun } from 'src/app/invoices/model/invoice-run.model';
import { InvoiceRunsService } from '../invoice-runs.service';

export class InvoiceRunsListDataSource implements DataSource<InvoiceRun> {
  private invoiceRunsSubject = new BehaviorSubject<InvoiceRun[]>([]);
  public invoiceRuns = this.invoiceRunsSubject.asObservable();
  public totalElements = 0;
  public loading = false;
  private lastPage: Page<InvoiceRun>;
  private refreshHeartbeat: Subscription;
  private invoiceRunsToRefresh: InvoiceRun[] = [];

  constructor(private invoiceRunsService: InvoiceRunsService) { }

  connect(collectionViewer: CollectionViewer): Observable<InvoiceRun[]> {
    this.refreshHeartbeat = interval(500).subscribe(() => this.refreshInvoiceRuns());
    return this.invoiceRuns;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.refreshHeartbeat.unsubscribe();
    this.invoiceRunsSubject.complete();
  }

  loadInvoiceRuns(pageDefinition: PageDefinition, callback: (page: Page<InvoiceRun>) => void = () => { }) {
    this.loading = true;
    this.invoiceRunsToRefresh = [];
    this.invoiceRunsService.findInvoiceRuns(pageDefinition)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<InvoiceRun>>(page => this.totalElements = page.totalElements),
        tap<Page<InvoiceRun>>(page => callback(page)),
        tap<Page<InvoiceRun>>(page => this.lastPage = page),
        tap<Page<InvoiceRun>>(page => this.autoRefreshInvoiceRunsInProgress(page))
      )
      .subscribe(page => this.invoiceRunsSubject.next(page.content));
  }

  private autoRefreshInvoiceRunsInProgress(page: Page<InvoiceRun>): void {
    page.content.forEach(i => {
      if (i.status === 'STARTED') {
        this.autoRefresh(i);
      }
    });
  }

  public autoRefresh(invoiceRun: InvoiceRun) {
    this.invoiceRunsToRefresh.push(invoiceRun);
  }

  private refreshInvoiceRuns() {
    this.invoiceRunsToRefresh.forEach(i => this.refreshInvoiceRun(i));
  }

  private refreshInvoiceRun(invoiceRun: InvoiceRun): void {
    this.invoiceRunsService
      .getInvoiceRun(invoiceRun.id)
      .subscribe(i => {
        this.redrawLastPageReplacing(i);
        this.stopRefreshingWhenFinished(i);
      });
  }

  private redrawLastPageReplacing(invoiceRun: InvoiceRun) {
    const index = this.lastPage.indexOf(invoiceRun);
    if (index < 0) {
      return;
    }
    const updatedContent = this.lastPage.content.slice();
    updatedContent[index] = invoiceRun;
    this.invoiceRunsSubject.next(updatedContent);
  }

  private stopRefreshingWhenFinished(invoiceRun: InvoiceRun) {
    if (invoiceRun.status === 'FINISHED') {
      this.invoiceRunsToRefresh = this.invoiceRunsToRefresh.filter(i => i.id !== invoiceRun.id);
    }
  }
}
