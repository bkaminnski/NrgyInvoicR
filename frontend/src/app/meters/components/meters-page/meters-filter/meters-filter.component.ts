import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';

@Component({
  selector: 'app-meters-filter',
  templateUrl: './meters-filter.component.html',
  styleUrls: ['./meters-filter.component.scss']
})
export class MetersFilterComponent implements OnInit {
  @Output() searchEvent = new EventEmitter();
  metersSearchCriteria: MetersSearchCriteria;

  constructor() {
    this.metersSearchCriteria = new MetersSearchCriteria('');
  }

  ngOnInit() { }

  search() {
    this.searchEvent.emit(this.metersSearchCriteria);
  }
}
