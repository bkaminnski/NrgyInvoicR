import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpParams, HttpClient } from '@angular/common/http';
import { Client } from '../../model/client.model';
import { ClientPlanAssignment } from '../../model/client-plan-assignment.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Injectable()
export class ClientPlanAssignmentsService {

  constructor(private http: HttpClient) { }

  saveClientPlanAssignment(client: Client, clientPlanAssignment: ClientPlanAssignment): Observable<ClientPlanAssignment> {
    if (clientPlanAssignment.isNew()) {
      return this.http.post<ClientPlanAssignment>('/api/clients/' + client.id + '/planAssignments', clientPlanAssignment);
    } else {
      return this.http.put<ClientPlanAssignment>('/api/clients/' + client.id + '/planAssignments/' + clientPlanAssignment.id, clientPlanAssignment);
    }
  }

  findClientPlanAssignments(client: Client, pageDefinition: PageDefinition): Observable<Page<ClientPlanAssignment>> {
    let params = new HttpParams();
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<ClientPlanAssignment>>('/api/clients/' + client.id + '/planAssignments', { params: params }).pipe(
      map(Page.cloned)
    );
  }
}
