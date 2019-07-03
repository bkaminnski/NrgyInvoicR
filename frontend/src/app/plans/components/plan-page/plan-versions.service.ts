import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Plan } from 'src/app/plans/model/plan.model';
import { PlanVersion } from '../../model/plan-version.model';
import { map } from 'rxjs/operators';

@Injectable()
export class PlanVersionsService {

  constructor(private http: HttpClient) { }

  savePlanVersion(plan: Plan, planVersion: PlanVersion): Observable<PlanVersion> {
    if (planVersion.isNew()) {
      return this.http.post<PlanVersion>('/api/plans/' + plan.id + '/versions', planVersion);
    } else {
      return this.http.put<PlanVersion>('/api/plans/' + plan.id + '/versions/' + planVersion.id, planVersion);
    }
  }

  findPlanVersions(plan: Plan, pageDefinition: PageDefinition): Observable<Page<PlanVersion>> {
    let params = new HttpParams();
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<PlanVersion>>('/api/plans/' + plan.id + '/versions', { params: params }).pipe(
      map(Page.cloned)
    );
  }
}
