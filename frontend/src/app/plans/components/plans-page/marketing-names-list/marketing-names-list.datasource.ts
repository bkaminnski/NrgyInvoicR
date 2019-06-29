import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { MarketingName } from 'src/app/plans/model/marketing-name.model';
import { MarketingNamesListService } from './marketing-names-list.service';

export class MarketingNamesListDataSource implements DataSource<MarketingName> {
  private marketingNamesSubject = new BehaviorSubject<MarketingName[]>([]);
  public marketingNames = this.marketingNamesSubject.asObservable();
  public totalElements = 0;
  public loading = false;

  constructor(private marketingNamesListService: MarketingNamesListService) { }

  connect(collectionViewer: CollectionViewer): Observable<MarketingName[]> {
    return this.marketingNames;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.marketingNamesSubject.complete();
  }

  loadMarketingNames(pageDefinition: PageDefinition) {
    this.loading = true;
    this.marketingNamesListService.findMarketingNames(pageDefinition)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<MarketingName>>(page => this.totalElements = page.totalElements)
      )
      .subscribe(page => this.marketingNamesSubject.next(page.content));
  }
}
