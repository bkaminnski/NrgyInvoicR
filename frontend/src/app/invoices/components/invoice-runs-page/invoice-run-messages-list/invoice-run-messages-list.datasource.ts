import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { Observable, BehaviorSubject, of } from 'rxjs';
import { InvoiceRun } from 'src/app/invoices/model/invoice-run.model';
import { InvoiceRunMessage } from 'src/app/invoices/model/invoice-run-message.model';
import { InvoiceRunsService } from '../invoice-runs.service';
import { catchError, tap } from 'rxjs/operators';

export class InvoiceRunMessagesListDataSource implements DataSource<InvoiceRunMessage> {
  private invoiceRunMessagesSubject: BehaviorSubject<InvoiceRunMessage[]>;
  public invoiceRunMessages: Observable<InvoiceRunMessage[]>;
  public totalElements = 0;

  constructor(private invoiceRunsService: InvoiceRunsService, initialInvoiceRunMessages: InvoiceRunMessage[]) {
    if (initialInvoiceRunMessages && initialInvoiceRunMessages.length) {
      this.totalElements = initialInvoiceRunMessages.length;
    }
    this.invoiceRunMessagesSubject = new BehaviorSubject<InvoiceRunMessage[]>(initialInvoiceRunMessages);
    this.invoiceRunMessages = this.invoiceRunMessagesSubject.asObservable();
  }

  connect(collectionViewer: CollectionViewer): Observable<InvoiceRunMessage[]> {
    return this.invoiceRunMessages;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.invoiceRunMessagesSubject.complete();
  }

  loadInvoiceRunMessages(invoiceRun: InvoiceRun) {
    this.invoiceRunsService.getInvoiceRunMessages(invoiceRun.id)
      .pipe(
        catchError(() => of([])),
        tap<InvoiceRunMessage[]>(invoiceRunMessages => this.totalElements = invoiceRunMessages.length)
      )
      .subscribe(invoiceRunMessages => this.invoiceRunMessagesSubject.next(invoiceRunMessages));
  }
}
