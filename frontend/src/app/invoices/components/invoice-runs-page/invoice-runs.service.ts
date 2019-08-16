import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { InvoiceRun } from '../../model/invoice-run.model';
import { InvoiceRunMessage } from '../../model/invoice-run-message.model';
import { map } from 'rxjs/operators';

@Injectable()
export class InvoiceRunsService {

  constructor(private http: HttpClient) { }

  saveInvoiceRun(invoiceRun: InvoiceRun): Observable<InvoiceRun> {
    if (invoiceRun.isNew()) {
      return this.http.post<InvoiceRun>('/api/invoiceRuns', invoiceRun);
    } else {
      return this.http.put<InvoiceRun>('/api/invoiceRuns/' + invoiceRun.id, invoiceRun);
    }
  }

  getInvoiceRun(id: number): Observable<InvoiceRun> {
    return this.http.get<InvoiceRun>('/api/invoiceRuns/' + id);
  }

  getInvoiceRunMessages(id: number): Observable<InvoiceRunMessage[]> {
    return this.http.get<InvoiceRunMessage[]>('/api/invoiceRuns/' + id + '/messages');
  }

  findInvoiceRuns(pageDefinition: PageDefinition): Observable<Page<InvoiceRun>> {
    let params = new HttpParams();
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<InvoiceRun>>('/api/invoiceRuns', { params: params }).pipe(
      map(Page.cloned)
    );
  }

  prepareNewInvoiceRun(): Observable<InvoiceRun> {
    return this.http.get<InvoiceRun>('/api/invoiceRuns/new');
  }

  startInvoiceRun(invoiceRun: InvoiceRun): Observable<InvoiceRun> {
    return this.http.post<InvoiceRun>('/api/invoiceRuns/' + invoiceRun.id + '/start', invoiceRun);
  }
}
