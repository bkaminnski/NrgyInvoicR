import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-plan-versions-list',
  templateUrl: './plan-versions-list.component.html',
  styleUrls: ['./plan-versions-list.component.scss']
})
export class PlanVersionsListComponent implements OnInit {
  public planId: any;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.planId = this.route.snapshot.paramMap.get('id');
  }
}
