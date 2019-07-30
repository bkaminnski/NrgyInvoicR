import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Plan } from 'src/app/plans/model/plan.model';
import { InvoiceRun } from '../../model/invoice-run.model';
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

  findInvoiceRuns(pageDefinition: PageDefinition): Observable<Page<InvoiceRun>> {
    let params = new HttpParams();
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<InvoiceRun>>('/api/invoiceRuns', { params: params }).pipe(
      map(Page.cloned)
    );
  }
}
