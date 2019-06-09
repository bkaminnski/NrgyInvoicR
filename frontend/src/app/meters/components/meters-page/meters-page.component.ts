import { Component, OnInit } from '@angular/core';
import { MetersService } from './meters.service';

@Component({
  templateUrl: './meters-page.component.html',
  styleUrls: ['./meters-page.component.scss'],
  providers: [
    MetersService
  ]
})
export class MetersPageComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
