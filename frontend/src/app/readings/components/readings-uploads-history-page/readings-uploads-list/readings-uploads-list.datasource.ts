import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { ReadingUpload } from 'src/app/readings/model/reading-upload.model';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { ReadingsUploadsListService } from './readings-uploads-list.service';
import { ReadingsUploadsSearchCriteria } from 'src/app/readings/model/readings-uploads-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';

export class ReadingsUploadsListDataSource implements DataSource<ReadingUpload> {
  private readingsUploadsSubject = new BehaviorSubject<ReadingUpload[]>([]);
  private totalElementsSubject = new BehaviorSubject<number>(0);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  public readingsUploads = this.readingsUploadsSubject.asObservable();
  public totalElements = this.totalElementsSubject.asObservable();
  public loading = this.loadingSubject.asObservable();

  constructor(private readingsUploadsListService: ReadingsUploadsListService) { }

  connect(collectionViewer: CollectionViewer): Observable<ReadingUpload[]> {
    return this.readingsUploads;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.readingsUploadsSubject.complete();
    this.totalElementsSubject.complete();
    this.loadingSubject.complete();
  }

  loadReadingsUploads(readingsUploadsSearchCriteria: ReadingsUploadsSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize) {
    this.loadingSubject.next(true);
    this.readingsUploadsListService.findReadingsUploads(readingsUploadsSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loadingSubject.next(false)),
        tap<Page<ReadingUpload>>(page => this.totalElementsSubject.next(page.totalElements))
      )
      .subscribe(page => this.readingsUploadsSubject.next(page.content));
  }
}
