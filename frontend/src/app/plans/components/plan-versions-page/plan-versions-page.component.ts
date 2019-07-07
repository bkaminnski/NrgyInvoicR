import { Component, OnInit } from '@angular/core';
import { PlanVersionsService } from './plan-versions.service';

@Component({
  templateUrl: './plan-versions-page.component.html',
  providers: [
    PlanVersionsService
  ]
})
export class PlanVersionsPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
