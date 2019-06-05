import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { Meter } from 'src/app/meters/model/meter.model';
import { MetersListService } from './meters-list.service';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';

export class MetersListDataSource implements DataSource<Meter> {
  private metersSubject = new BehaviorSubject<Meter[]>([]);
  private totalElementsSubject = new BehaviorSubject<number>(0);
  private loadingSubject = new BehaviorSubject<boolean>(false);

  public meters = this.metersSubject.asObservable();
  public totalElements = this.totalElementsSubject.asObservable();
  public loading = this.loadingSubject.asObservable();

  constructor(private metersListService: MetersListService) { }

  connect(collectionViewer: CollectionViewer): Observable<Meter[]> {
    return this.meters;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.metersSubject.complete();
    this.totalElementsSubject.complete();
    this.loadingSubject.complete();
  }

  loadMeters(metersSearchCriteria: MetersSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize) {
    this.loadingSubject.next(true);
    this.metersListService.findMeters(metersSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loadingSubject.next(false)),
        tap<Page<Meter>>(page => this.totalElementsSubject.next(page.totalElements))
      )
      .subscribe(page => this.metersSubject.next(page.content));
  }
}
