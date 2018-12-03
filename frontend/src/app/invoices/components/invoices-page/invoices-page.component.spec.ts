import { async, TestBed, ComponentFixture } from '@angular/core/testing';
import { InvoicesPageComponent } from './invoices-page.component';
import { Component, EventEmitter, Output } from '@angular/core';
import { InvoicesSearchCriteria } from '../../model/invoices-search-criteria.model';
import { By } from '@angular/platform-browser';

@Component({
  selector: 'app-invoices-filter',
  template: 'mock invoices filter component'
})
class MockInvoicesFilterComponent {
  @Output() searchEvent = new EventEmitter();

  emit(invoicesSearchCriteria: InvoicesSearchCriteria) {
    this.searchEvent.emit(invoicesSearchCriteria);
  }
}

@Component({
  selector: 'app-invoices-list',
  template: 'mock invoices list component'
})
class MockInvoicesListComponent {

  search(invoicesSearchCriteria: InvoicesSearchCriteria) { }
}

describe('InvoicesPageComponent', () => {

  let fixture: ComponentFixture<InvoicesPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [],
      declarations: [
        InvoicesPageComponent,
        MockInvoicesListComponent,
        MockInvoicesFilterComponent
      ],
      providers: [],
      schemas: []
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InvoicesPageComponent);
  });

  it('should call search on app-invoices-list once searchEvent is emitted from app-invoices-filter component', () => {
    const invoicesFilterComponent: MockInvoicesFilterComponent = fixture.debugElement.query(By.css('app-invoices-filter')).componentInstance;
    const invoicesListComponent: MockInvoicesListComponent = fixture.debugElement.query(By.css('app-invoices-list')).componentInstance;
    const invoicesSearchCriteria = new InvoicesSearchCriteria();
    spyOn(invoicesListComponent, 'search');

    invoicesFilterComponent.emit(invoicesSearchCriteria);

    expect(invoicesListComponent.search).toHaveBeenCalledWith(invoicesSearchCriteria);
  });
});
