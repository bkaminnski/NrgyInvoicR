import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice } from '../../../model/invoice.model';
import { InvoicesSearchCriteria } from '../../../model/invoices-search-criteria.model';

@Injectable()
export class InvoicesListService {

  constructor(private http: HttpClient) { }

  findInvoices(invoicesSearchCriteria: InvoicesSearchCriteria): Observable<Invoice[]> {
    let params = new HttpParams();
    if (invoicesSearchCriteria.issueDateFrom) {
      params = params.append('issueDateFrom', invoicesSearchCriteria.issueDateFrom.toISOString());
    }
    if (invoicesSearchCriteria.issueDateTo) {
      params = params.append('issueDateTo', invoicesSearchCriteria.issueDateTo.clone().add(1, 'day').toISOString());
    }
    return this.http.get<Invoice[]>('/api/invoices', { params: params });
  }
}
