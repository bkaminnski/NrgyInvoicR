import { Component, OnInit, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { ClientsSearchCriteria } from 'src/app/clients/model/clients-search-criteria.model';

@Component({
  selector: 'app-clients-filter',
  templateUrl: './clients-filter.component.html',
  styleUrls: ['./clients-filter.component.scss']
})
export class ClientsFilterComponent implements OnInit, AfterViewInit {
  @Output() searchEvent = new EventEmitter();
  clientsSearchCriteria: ClientsSearchCriteria;

  constructor() {
    this.clientsSearchCriteria = new ClientsSearchCriteria();
  }

  ngOnInit() { }

  ngAfterViewInit() {
    setTimeout(() => this.search());
  }

  search() {
    this.searchEvent.emit(this.clientsSearchCriteria);
  }
}
