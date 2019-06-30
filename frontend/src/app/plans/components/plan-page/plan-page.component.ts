import { Component, OnInit } from '@angular/core';
import { PlanVersionsListService } from './plan-versions-list/plan-versions-list.service';
import { PlansService } from '../plans.service';

@Component({
  templateUrl: './plan-page.component.html',
  providers: [
    PlansService,
    PlanVersionsListService
  ]
})
export class PlanPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
