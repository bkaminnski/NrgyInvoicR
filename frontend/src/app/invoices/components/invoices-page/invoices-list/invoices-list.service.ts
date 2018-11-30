import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice } from '../../../model/invoice.model';
import { InvoicesSearchCriteria } from '../../../model/invoices-search-criteria.model';
import { Page } from 'src/app/invoices/model/page.model';

@Injectable()
export class InvoicesListService {

  constructor(private http: HttpClient) { }

  findInvoices(invoicesSearchCriteria: InvoicesSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize): Observable<Page<Invoice>> {
    let params = new HttpParams();

    if (invoicesSearchCriteria.issueDateFrom) {
      params = params.append('issueDateFrom', invoicesSearchCriteria.issueDateFrom.toISOString());
    }

    if (invoicesSearchCriteria.issueDateTo) {
      params = params.append('issueDateTo', invoicesSearchCriteria.issueDateTo.clone().add(1, 'day').toISOString());
    }

    params = params
      .append('pageDefinition.sortColumn', sortColumn)
      .append('pageDefinition.sortDirection', sortDirection)
      .append('pageDefinition.pageNumber', pageIndex)
      .append('pageDefinition.pageSize', pageSize);

    return this.http.get<Page<Invoice>>('/api/invoices', { params: params });
  }
}
