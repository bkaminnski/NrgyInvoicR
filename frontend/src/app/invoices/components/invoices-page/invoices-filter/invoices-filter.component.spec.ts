import { async, TestBed, ComponentFixture } from '@angular/core/testing';
import { InvoicesFilterComponent } from './invoices-filter.component';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from 'src/app/core/material.module';
import { InvoicesSearchCriteria } from 'src/app/invoices/model/invoices-search-criteria.model';
import * as moment from 'moment';

describe('InvoicesFilterComponent', () => {

  const TODAY = '2018-11-17';
  const BEGINNING_OF_MONTH = '2018-11-01';
  const END_OF_MONTH = '2018-11-30';

  let fixture: ComponentFixture<InvoicesFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        MaterialModule
      ],
      declarations: [
        InvoicesFilterComponent
      ],
      providers: [],
      schemas: []
    }).compileComponents();
  }));

  beforeEach(() => {
    const today: Date = new Date(TODAY);
    jasmine.clock().mockDate(today);
    fixture = TestBed.createComponent(InvoicesFilterComponent);
  });

  it('should emit searchEvent on init with date range covering current month', (done: DoneFn) => {
    const invoicesFilterComponent: InvoicesFilterComponent = fixture.componentInstance;
    invoicesFilterComponent.searchEvent.subscribe((invoicesSearchCriteria: InvoicesSearchCriteria) => {
      expect(invoicesSearchCriteria).toBeDefined();
      expect(invoicesSearchCriteria.issueDateSince.toDate()).toEqual(moment(BEGINNING_OF_MONTH).toDate());
      expect(invoicesSearchCriteria.issueDateUntil.toDate()).toEqual(moment(END_OF_MONTH).toDate());
      done();
    });
    invoicesFilterComponent.ngOnInit();
  });
});
