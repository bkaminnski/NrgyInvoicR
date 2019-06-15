import { Component, OnInit } from '@angular/core';
import { ClientsService } from './clients.service';

@Component({
  templateUrl: './clients-page.component.html',
  styleUrls: ['./clients-page.component.scss'],
  providers: [
    ClientsService
  ]
})
export class ClientsPageComponent implements OnInit {

  constructor() { }

  ngOnInit() {}
}
