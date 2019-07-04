import { Component, OnInit, Input } from '@angular/core';
import { FlattenedBucketsListDataSource } from './flattened-buckets-list.datasource';
import { Observable } from 'rxjs';
import { FlattenedBucket } from 'src/app/plans/model/flattened-bucket.model';

@Component({
  selector: 'app-flattened-buckets-list',
  templateUrl: './flattened-buckets-list.component.html',
  styleUrls: ['./flattened-buckets-list.component.scss']
})
export class FlattenedBucketsListComponent implements OnInit {
  @Input() flattenedBucketsObservable: Observable<FlattenedBucket[]>;
  public dataSource: FlattenedBucketsListDataSource;
  public displayedColumns: string[] = ['description', 'price'];

  constructor() { }

  ngOnInit() {
    this.dataSource = new FlattenedBucketsListDataSource(this.flattenedBucketsObservable);
  }
}
