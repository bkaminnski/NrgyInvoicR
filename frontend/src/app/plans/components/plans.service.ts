import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Plan } from '../model/plan.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Injectable({
  providedIn: 'root'
})
export class PlansService {

  constructor(private http: HttpClient) { }

  findPlans(pageDefinition: PageDefinition): Observable<Page<Plan>> {
    let params = new HttpParams();
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<Plan>>('/api/plans', { params: params });
  }

  getPlan(id: number): Observable<Plan> {
    return this.http.get<Plan>('/api/plans/' + id);
  }
}
