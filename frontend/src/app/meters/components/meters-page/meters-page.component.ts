import { Component, OnInit } from '@angular/core';
import { MetersListService } from './meters-list/meters-list.service';

@Component({
  templateUrl: './meters-page.component.html',
  styleUrls: ['./meters-page.component.scss'],
  providers: [
    MetersListService
  ]
})
export class MetersPageComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
