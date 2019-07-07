import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Client } from 'src/app/clients/model/client.model';
import { ClientsService } from '../../clients.service';
import { ClientsSearchCriteria } from 'src/app/clients/model/clients-search-criteria.model';

export class ClientsListDataSource implements DataSource<Client> {
  private clientsSubject = new BehaviorSubject<Client[]>([]);
  public clients = this.clientsSubject.asObservable();
  public totalElements = 0;
  public loading = false;

  constructor(private clientsService: ClientsService) { }

  connect(collectionViewer: CollectionViewer): Observable<Client[]> {
    return this.clients;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.clientsSubject.complete();
  }

  loadClients(clientsSearchCriteria: ClientsSearchCriteria, pageDefinition: PageDefinition, callback: (page: Page<Client>) => void = () => { }) {
    this.loading = true;
    this.clientsService.findClients(clientsSearchCriteria, pageDefinition)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<Client>>(page => this.totalElements = page.totalElements),
        tap<Page<Client>>(page => callback(page))
      )
      .subscribe(page => this.clientsSubject.next(page.content));
  }
}
