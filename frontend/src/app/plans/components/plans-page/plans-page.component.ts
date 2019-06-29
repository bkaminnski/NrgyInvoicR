import { Component, OnInit } from '@angular/core';
import { MarketingNamesListService } from './marketing-names-list/marketing-names-list.service';

@Component({
  selector: 'app-plans-page',
  templateUrl: './plans-page.component.html',
  providers: [
    MarketingNamesListService
  ]
})
export class PlansPageComponent implements OnInit {

  constructor() { }

  ngOnInit() { }
}
