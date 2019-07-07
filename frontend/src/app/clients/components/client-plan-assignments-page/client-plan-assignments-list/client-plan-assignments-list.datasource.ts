import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { catchError, finalize, tap } from 'rxjs/operators';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Client } from 'src/app/clients/model/client.model';
import { ClientPlanAssignment } from 'src/app/clients/model/client-plan-assignment.model';
import { ClientPlanAssignmentsService } from '../client-plan-assignments.service';

export class ClientPlanAssignmentsListDataSource implements DataSource<ClientPlanAssignment> {
  private clientPlanAssignmentsSubject = new BehaviorSubject<ClientPlanAssignment[]>([]);
  public clientPlanAssignments = this.clientPlanAssignmentsSubject.asObservable();
  public totalElements = 0;
  public loading = false;

  constructor(private clientPlanAssignmentsService: ClientPlanAssignmentsService) { }

  connect(collectionViewer: CollectionViewer): Observable<ClientPlanAssignment[]> {
    return this.clientPlanAssignments;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.clientPlanAssignmentsSubject.complete();
  }

  loadClientPlanAssignments(client: Client, pageDefinition: PageDefinition, callback: (page: Page<ClientPlanAssignment>) => void = () => { }) {
    this.loading = true;
    this.clientPlanAssignmentsService.findClientPlanAssignments(client, pageDefinition)
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false),
        tap<Page<ClientPlanAssignment>>(page => this.totalElements = page.totalElements),
        tap<Page<ClientPlanAssignment>>(page => callback(page))
      )
      .subscribe(page => this.clientPlanAssignmentsSubject.next(page.content));
  }
}
