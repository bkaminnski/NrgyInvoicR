import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { Invoice } from 'src/app/invoices/model/invoice.model';
import { InvoicesListService } from './invoices-list.service';
import { InvoicesSearchCriteria } from 'src/app/invoices/model/invoices-search-criteria.model';

export class InvoicesListDataSource implements DataSource<Invoice> {
  private invoicesSubject = new BehaviorSubject<Invoice[]>([]);
  public invoices = this.invoicesSubject.asObservable();
  public totalElements = 0;
  public loading = false;

  constructor(private invoicesListService: InvoicesListService) { }

  connect(collectionViewer: CollectionViewer): Observable<Invoice[]> {
    return this.invoices;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.invoicesSubject.complete();
  }

  loadInvoices(invoicesSearchCriteria: InvoicesSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize) {
    this.loading = true;
    this.invoicesListService.findInvoices(invoicesSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<Invoice>>(page => this.totalElements = page.totalElements)
      )
      .subscribe(page => this.invoicesSubject.next(page.content));
  }
}
