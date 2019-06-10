import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { ReadingUpload } from 'src/app/readings/model/reading-upload.model';
import { ReadingsUploadsListService } from './readings-uploads-list.service';
import { ReadingsUploadsSearchCriteria } from 'src/app/readings/model/readings-uploads-search-criteria.model';

export class ReadingsUploadsListDataSource implements DataSource<ReadingUpload> {
  private readingsUploadsSubject = new BehaviorSubject<ReadingUpload[]>([]);
  public readingsUploads = this.readingsUploadsSubject.asObservable();
  public totalElements = 0;
  public loading = false;

  constructor(private readingsUploadsListService: ReadingsUploadsListService) { }

  connect(collectionViewer: CollectionViewer): Observable<ReadingUpload[]> {
    return this.readingsUploads;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.readingsUploadsSubject.complete();
  }

  loadReadingsUploads(readingsUploadsSearchCriteria: ReadingsUploadsSearchCriteria, pageDefinition: PageDefinition) {
    this.loading = true;
    this.readingsUploadsListService.findReadingsUploads(readingsUploadsSearchCriteria, pageDefinition)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<ReadingUpload>>(page => this.totalElements = page.totalElements)
      )
      .subscribe(page => this.readingsUploadsSubject.next(page.content));
  }
}
