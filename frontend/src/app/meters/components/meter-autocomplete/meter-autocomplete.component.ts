import { Component, OnInit, OnDestroy, forwardRef, Input } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Subject, Subscription, BehaviorSubject, of } from 'rxjs';
import { debounceTime, catchError, finalize, tap } from 'rxjs/operators';
import { Meter } from '../../model/meter.model';
import { MetersService } from '../meters.service';
import { MetersSearchCriteria } from '../../model/meters-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { NotificationService } from 'src/app/core/components/notification/notification.service';

@Component({
  selector: 'app-meter-autocomplete',
  templateUrl: './meter-autocomplete.component.html',
  styleUrls: ['./meter-autocomplete.component.scss'],
  providers: [
    MetersService,
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => MeterAutocompleteComponent),
      multi: true
    }
  ]
})
export class MeterAutocompleteComponent implements OnInit, OnDestroy, ControlValueAccessor {
  private serialNumbersSubject = new Subject<string>();
  private serialNumbersSubscription: Subscription;
  private metersSubject = new BehaviorSubject<Meter[]>([]);
  private meter: Meter;
  public loading = false;
  public meters = this.metersSubject.asObservable();
  @Input() required = false;

  constructor(private metersService: MetersService, private notificationService: NotificationService) { }

  ngOnInit() {
    this.serialNumbersSubscription = this.serialNumbersSubject
      .asObservable()
      .pipe(
        debounceTime(300)
      )
      .subscribe(serialNumber => this.findMeters(serialNumber));
    this.serialNumbersSubject.next('');
  }

  ngOnDestroy(): void {
    this.metersSubject.complete();
    this.serialNumbersSubscription.unsubscribe();
  }

  writeValue(meter: any): void {
    this.meter = meter;
  }

  registerOnChange(fn: any): void {
    this.propagateChange = fn;
  }

  registerOnTouched(fn: any): void { }

  setDisabledState?(isDisabled: boolean): void { }

  get selectedMeter() {
    return this.meter;
  }

  set selectedMeter(meter: Meter) {
    this.meter = meter;
    this.propagateChange(this.meter);
  }

  meterAsString(meter: Meter): string {
    if (meter) {
      return meter.serialNumber;
    }
    return '';
  }

  discardNonExistingSerialNumber() {
    if (this.meter && !this.meter.id) {
      this.selectedMeter = null;
    }
  }

  serialNumberChanged(serialNumber: string) {
    this.serialNumbersSubject.next(serialNumber);
  }

  private findMeters(serialNumber: string): void {
    this.loading = true;
    this.metersService.findMeters(new MetersSearchCriteria(serialNumber, true), new PageDefinition('serialNumber', 'asc', 0, 10))
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap((page: Page<Meter>) => this.notifyWhenThereIsNoUnassignedMeter(page.totalElements, serialNumber))
      )
      .subscribe((page: Page<Meter>) => this.metersSubject.next(page.content));
  }

  private notifyWhenThereIsNoUnassignedMeter(totalElements: number, serialNumber: string): void {
    if (totalElements === 0 && serialNumber === '') {
      this.notificationService.info('There is no meter that can be assigned to a client.');
    }
  }

  private propagateChange = (_: any) => { };
}
