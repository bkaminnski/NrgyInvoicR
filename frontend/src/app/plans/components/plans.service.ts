import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Plan } from '../model/plan.model';
import { PlansSearchCriteria } from '../model/plans-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Injectable({
  providedIn: 'root'
})
export class PlansService {

  constructor(private http: HttpClient) { }

  findPlans(plansSearchCriteria: PlansSearchCriteria, pageDefinition: PageDefinition): Observable<Page<Plan>> {
    let params = new HttpParams();
    if (plansSearchCriteria.name) {
      params = params.append('name', plansSearchCriteria.name);
    }
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<Plan>>('/api/plans', { params: params }).pipe(
      map(Page.cloned)
    );
  }

  getPlan(id: number): Observable<Plan> {
    return this.http.get<Plan>('/api/plans/' + id);
  }
}
