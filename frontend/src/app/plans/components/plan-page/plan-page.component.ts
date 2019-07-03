import { Component, OnInit } from '@angular/core';
import { PlanVersionsService } from './plan-versions.service';

@Component({
  templateUrl: './plan-page.component.html',
  providers: [
    PlanVersionsService
  ]
})
export class PlanPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
