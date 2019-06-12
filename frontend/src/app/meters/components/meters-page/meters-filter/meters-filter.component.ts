import { Component, OnInit, Output, EventEmitter, AfterViewInit } from '@angular/core';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';

@Component({
  selector: 'app-meters-filter',
  templateUrl: './meters-filter.component.html',
  styleUrls: ['./meters-filter.component.scss']
})
export class MetersFilterComponent implements OnInit, AfterViewInit {
  @Output() searchEvent = new EventEmitter();
  metersSearchCriteria: MetersSearchCriteria;

  constructor() {
    this.metersSearchCriteria = new MetersSearchCriteria('');
  }

  ngOnInit() { }

  ngAfterViewInit() {
    setTimeout(() => this.search());
  }

  search() {
    this.searchEvent.emit(this.metersSearchCriteria);
  }
}
