import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Invoice } from '../../../model/invoice.model';
import { InvoicesSearchCriteria } from '../../../model/invoices-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Injectable()
export class InvoicesListService {

  constructor(private http: HttpClient) { }

  findInvoices(invoicesSearchCriteria: InvoicesSearchCriteria, pageDefinition: PageDefinition): Observable<Page<Invoice>> {
    let params = new HttpParams();
    if (invoicesSearchCriteria.issueDateSince) {
      params = params.append('issueDateSince', invoicesSearchCriteria.issueDateSince.toISOString());
    }
    if (invoicesSearchCriteria.issueDateUntil) {
      params = params.append('issueDateUntil', invoicesSearchCriteria.issueDateUntil.clone().add(1, 'day').toISOString());
    }
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<Invoice>>('/api/invoices', { params: params });
  }
}
