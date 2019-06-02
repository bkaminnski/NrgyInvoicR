import { Component, OnInit } from '@angular/core';
import { ReadingsUploadsListService } from './readings-uploads-list/readings-uploads-list.service';

@Component({
  templateUrl: './readings-uploads-history-page.component.html',
  styleUrls: ['./readings-uploads-history-page.component.scss'],
  providers: [
    ReadingsUploadsListService
  ]
})
export class ReadingsUploadsHistoryPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }

}
