import { Component, OnInit } from '@angular/core';
import { PlansListService } from './plans-list/plans-list.service';

@Component({
  templateUrl: './plans-page.component.html',
  providers: [
    PlansListService
  ]
})
export class PlansPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
