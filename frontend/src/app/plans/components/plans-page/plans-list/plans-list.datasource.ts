import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Plan } from 'src/app/plans/model/plan.model';
import { PlansService } from '../../plans.service';
import { PlansSearchCriteria } from 'src/app/plans/model/plans-search-criteria.model';

export class PlansListDataSource implements DataSource<Plan> {
  private plansSubject = new BehaviorSubject<Plan[]>([]);
  public plans = this.plansSubject.asObservable();
  public totalElements = 0;
  public loading = false;

  constructor(private plansService: PlansService) { }

  connect(collectionViewer: CollectionViewer): Observable<Plan[]> {
    return this.plans;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.plansSubject.complete();
  }

  loadPlans(pageDefinition: PageDefinition) {
    this.loading = true;
    this.plansService.findPlans(new PlansSearchCriteria(), pageDefinition)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<Plan>>(page => this.totalElements = page.totalElements)
      )
      .subscribe(page => this.plansSubject.next(page.content));
  }
}
