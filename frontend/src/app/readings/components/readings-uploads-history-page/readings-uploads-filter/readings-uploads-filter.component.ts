import { Component, OnInit, EventEmitter, Output, AfterViewInit } from '@angular/core';
import { ReadingsUploadsSearchCriteria } from 'src/app/readings/model/readings-uploads-search-criteria.model';
import * as moment from 'moment';

@Component({
  selector: 'app-readings-uploads-filter',
  templateUrl: './readings-uploads-filter.component.html',
  styleUrls: ['./readings-uploads-filter.component.scss']
})
export class ReadingsUploadsFilterComponent implements OnInit, AfterViewInit {
  @Output() searchEvent = new EventEmitter();
  readingsUploadsSearchCriteria: ReadingsUploadsSearchCriteria;

  constructor() {
    this.readingsUploadsSearchCriteria = new ReadingsUploadsSearchCriteria(moment().startOf('month'), moment().endOf('month').startOf('day'), false);
  }

  ngOnInit() { }

  ngAfterViewInit() {
    this.search();
  }

  search() {
    this.searchEvent.emit(this.readingsUploadsSearchCriteria);
  }
}
