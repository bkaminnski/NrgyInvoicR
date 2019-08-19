import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { Observable, BehaviorSubject } from 'rxjs';
import { InvoiceRunMessage } from 'src/app/invoices/model/invoice-run-message.model';

export class InvoiceLinesListDataSource implements DataSource<InvoiceRunMessage> {
  private invoiceLinesSubject: BehaviorSubject<InvoiceRunMessage[]>;
  public invoiceLines: Observable<InvoiceRunMessage[]>;
  public totalElements = 0;

  constructor(initialInvoiceLines: InvoiceRunMessage[]) {
    if (initialInvoiceLines && initialInvoiceLines.length) {
      this.totalElements = initialInvoiceLines.length;
    }
    this.invoiceLinesSubject = new BehaviorSubject<InvoiceRunMessage[]>(initialInvoiceLines);
    this.invoiceLines = this.invoiceLinesSubject.asObservable();
  }

  connect(collectionViewer: CollectionViewer): Observable<InvoiceRunMessage[]> {
    return this.invoiceLines;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.invoiceLinesSubject.complete();
  }
}
