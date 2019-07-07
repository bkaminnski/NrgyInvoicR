import { Component, OnInit, OnDestroy, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Subject, Subscription, BehaviorSubject, of } from 'rxjs';
import { debounceTime, catchError, finalize } from 'rxjs/operators';
import { Plan } from '../../model/plan.model';
import { PlansService } from '../plans.service';
import { PlansSearchCriteria } from '../../model/plans-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Component({
  selector: 'app-plan-autocomplete',
  templateUrl: './plan-autocomplete.component.html',
  styleUrls: ['./plan-autocomplete.component.scss'],
  providers: [
    PlansService,
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PlanAutocompleteComponent),
      multi: true
    }
  ]
})
export class PlanAutocompleteComponent implements OnInit, OnDestroy, ControlValueAccessor {
  private namesSubject = new Subject<string>();
  private namesSubscription: Subscription;
  private plansSubject = new BehaviorSubject<Plan[]>([]);
  private plan: Plan;
  public loading = false;
  public plans = this.plansSubject.asObservable();
  @Input() required = false;

  constructor(private plansService: PlansService) { }

  ngOnInit() {
    this.namesSubscription = this.namesSubject
      .asObservable()
      .pipe(
        debounceTime(300)
      )
      .subscribe(name => this.findPlans(name));
    this.namesSubject.next('');
  }

  ngOnDestroy(): void {
    this.plansSubject.complete();
    this.namesSubscription.unsubscribe();
  }

  writeValue(plan: any): void {
    this.plan = plan;
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void { }

  setDisabledState?(isDisabled: boolean): void { }

  get selectedPlan() {
    return this.plan;
  }

  set selectedPlan(plan: Plan) {
    this.plan = plan;
    this.propagateChange(this.plan);
  }

  planAsString(plan: Plan): string {
    if (plan) {
      return plan.name;
    }
    return '';
  }

  discardNonExistingName() {
    if (this.plan && !this.plan.id) {
      this.selectedPlan = null;
    }
  }

  nameChanged(name: string) {
    this.namesSubject.next(name);
  }

  private findPlans(name: string): void {
    this.loading = true;
    this.plansService.findPlans(new PlansSearchCriteria(name), new PageDefinition('name', 'asc', 0, 10))
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
      )
      .subscribe((page: Page<Plan>) => this.plansSubject.next(page.content));
  }

  private propagateChange = (_: any) => { };
}
