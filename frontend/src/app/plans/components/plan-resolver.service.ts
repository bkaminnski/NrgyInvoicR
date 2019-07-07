import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Plan } from 'src/app/plans/model/plan.model';
import { PlansService } from './plans.service';

@Injectable({
  providedIn: 'root'
})
export class PlanResolverService implements Resolve<Plan> {

  constructor(private plansService: PlansService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Plan | Observable<Plan> | Promise<Plan> {
    const id = Number(route.paramMap.get('id'));
    return this.plansService.getPlan(id);
  }
}
