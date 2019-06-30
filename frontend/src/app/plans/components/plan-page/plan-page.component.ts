import { Component, OnInit } from '@angular/core';
import { PlanVersionsListService } from './plan-versions-list/plan-versions-list.service';

@Component({
  templateUrl: './plan-page.component.html',
  providers: [
    PlanVersionsListService
  ]
})
export class PlanPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
