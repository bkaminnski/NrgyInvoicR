import { Component, OnInit } from '@angular/core';
import { PlansService } from '../plans.service';

@Component({
  templateUrl: './plans-page.component.html',
  providers: [
    PlansService
  ]
})
export class PlansPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
