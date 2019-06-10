import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Meter } from 'src/app/meters/model/meter.model';
import { MetersService } from '../meters.service';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';

export class MetersListDataSource implements DataSource<Meter> {
  private metersSubject = new BehaviorSubject<Meter[]>([]);
  public meters = this.metersSubject.asObservable();
  public totalElements = 0;
  public loading = false;

  constructor(private metersService: MetersService) { }

  connect(collectionViewer: CollectionViewer): Observable<Meter[]> {
    return this.meters;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.metersSubject.complete();
  }

  loadMeters(metersSearchCriteria: MetersSearchCriteria, pageDefinition: PageDefinition) {
    this.loading = true;
    this.metersService.findMeters(metersSearchCriteria, pageDefinition)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<Meter>>(page => this.totalElements = page.totalElements)
      )
      .subscribe(page => this.metersSubject.next(page.content));
  }
}
