import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Client } from '../../model/client.model';
import { ClientsSearchCriteria } from '../../model/clients-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { map } from 'rxjs/operators';

@Injectable()
export class ClientsService {

  constructor(private http: HttpClient) { }

  saveClient(client: Client): Observable<Client> {
    if (client.isNew()) {
      return this.http.post<Client>('/api/clients', client);
    } else {
      return this.http.put<Client>('/api/clients/' + client.id, client);
    }
  }

  findClients(clientsSearchCriteria: ClientsSearchCriteria, pageDefinition: PageDefinition): Observable<Page<Client>> {
    let params = new HttpParams();
    if (clientsSearchCriteria.number) {
      params = params.append('number', clientsSearchCriteria.number);
    }
    if (clientsSearchCriteria.firstName) {
      params = params.append('firstName', clientsSearchCriteria.firstName);
    }
    if (clientsSearchCriteria.lastName) {
      params = params.append('lastName', clientsSearchCriteria.lastName);
    }
    if (clientsSearchCriteria.address) {
      params = params.append('address', clientsSearchCriteria.address);
    }
    if (clientsSearchCriteria.postalCode) {
      params = params.append('postalCode', clientsSearchCriteria.postalCode);
    }
    if (clientsSearchCriteria.city) {
      params = params.append('city', clientsSearchCriteria.city);
    }
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<Client>>('/api/clients', { params: params }).pipe(
      map(Page.cloned)
    );
  }
}
