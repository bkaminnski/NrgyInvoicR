import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { Observable } from 'rxjs';
import { FlattenedBucket } from 'src/app/plans/model/flattened-bucket.model';

export class FlattenedBucketsListDataSource implements DataSource<FlattenedBucket> {

  constructor(private flattenedBucketsObservable: Observable<FlattenedBucket[]>) { }

  connect(collectionViewer: CollectionViewer): Observable<FlattenedBucket[]> {
    return this.flattenedBucketsObservable;
  }

  disconnect(collectionViewer: CollectionViewer): void { }
}
