import { Component, OnInit } from '@angular/core';
import { ClientPlanAssignmentsService } from './client-plan-assignments.service';

@Component({
  templateUrl: './client-plan-assignments-page.component.html',
  providers: [
    ClientPlanAssignmentsService
  ]
})
export class ClientPlanAssignmentsPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
