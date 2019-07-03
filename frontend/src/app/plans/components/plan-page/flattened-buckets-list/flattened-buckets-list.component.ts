import { Component, OnInit } from '@angular/core';
import { FlattenedBucketsListDataSource } from './flattened-buckets-list.datasource';

@Component({
  selector: 'app-flattened-buckets-list',
  templateUrl: './flattened-buckets-list.component.html',
  styleUrls: ['./flattened-buckets-list.component.scss']
})
export class FlattenedBucketsListComponent implements OnInit {
  public dataSource: FlattenedBucketsListDataSource;
  public displayedColumns: string[] = ['description', 'price'];

  constructor() { }

  ngOnInit() { }
}
