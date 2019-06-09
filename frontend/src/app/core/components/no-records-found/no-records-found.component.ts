import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-no-records-found',
  templateUrl: './no-records-found.component.html',
  styleUrls: ['./no-records-found.component.scss']
})
export class NoRecordsFoundComponent implements OnInit {
  @Input() records: Array<any>;

  constructor() { }

  ngOnInit() {
  }
}
