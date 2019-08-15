import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
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

  constructor(private invoiceRunsService: InvoiceRunsService) { }

  connect(collectionViewer: CollectionViewer): Observable<InvoiceRun[]> {
    return this.invoiceRuns;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.invoiceRunsSubject.complete();
  }

  loadInvoiceRuns(pageDefinition: PageDefinition, callback: (page: Page<InvoiceRun>) => void = () => { }) {
    this.loading = true;
    this.invoiceRunsService.findInvoiceRuns(pageDefinition)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<InvoiceRun>>(page => this.totalElements = page.totalElements),
        tap<Page<InvoiceRun>>(page => callback(page)),
        tap<Page<InvoiceRun>>(page => this.lastPage = page)
      )
      .subscribe(page => this.invoiceRunsSubject.next(page.content));
  }

  showLastPageReplacing(invoiceRun: InvoiceRun) {
    const index = this.lastPage.indexOf(invoiceRun);
    if (index < 0) {
      return;
    }
    const updatedContent = this.lastPage.content.slice();
    updatedContent[index] = invoiceRun;
    this.invoiceRunsSubject.next(updatedContent);
  }
}
