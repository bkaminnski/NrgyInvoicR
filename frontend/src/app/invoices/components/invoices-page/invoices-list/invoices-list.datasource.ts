import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { Invoice } from 'src/app/invoices/model/invoice.model';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { InvoicesListService } from './invoices-list.service';
import { InvoicesSearchCriteria } from 'src/app/invoices/model/invoices-search-criteria.model';
import { Page } from 'src/app/invoices/model/page.model';

export class InvoicesListDataSource implements DataSource<Invoice> {

  private invoicesSubject = new BehaviorSubject<Invoice[]>([]);
  private totalElementsSubject = new BehaviorSubject<number>(0);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  public invoices = this.invoicesSubject.asObservable();
  public totalElements = this.totalElementsSubject.asObservable();
  public loading = this.loadingSubject.asObservable();

  constructor(private invoicesListService: InvoicesListService) { }

  connect(collectionViewer: CollectionViewer): Observable<Invoice[]> {
    return this.invoices;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.invoicesSubject.complete();
    this.totalElementsSubject.complete();
    this.loadingSubject.complete();
  }

  loadInvoices(invoicesSearchCriteria: InvoicesSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize) {
    this.loadingSubject.next(true);
    this.invoicesListService.findInvoices(invoicesSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loadingSubject.next(false)),
        tap<Page<Invoice>>(page => this.totalElementsSubject.next(page.totalElements))
      )
      .subscribe(page => this.invoicesSubject.next(page.content));
  }
}
