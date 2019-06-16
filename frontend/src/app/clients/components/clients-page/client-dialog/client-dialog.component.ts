import { Component, OnInit, Inject, ViewChild, AfterViewInit, OnDestroy } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Client } from 'src/app/clients/model/client.model';
import { ClientsService } from '../clients.service';
import { finalize, debounceTime, catchError, tap } from 'rxjs/operators';
import { NotificationService } from 'src/app/core/components/notification/notification.service';
import { Meter } from 'src/app/meters/model/meter.model';
import { MetersService } from 'src/app/meters/components/meters-page/meters.service';
import { Subject, Subscription, of, BehaviorSubject } from 'rxjs';
import { MetersSearchCriteria } from 'src/app/meters/model/meters-search-criteria.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Page } from 'src/app/core/model/page.model';

@Component({
  selector: 'app-client-dialog',
  templateUrl: './client-dialog.component.html',
  styleUrls: ['./client-dialog.component.scss'],
  providers: [
    ClientsService,
    MetersService
  ]
})
export class ClientDialogComponent implements OnInit, OnDestroy {
  public newClient: boolean;
  public client: Client;
  public loading: boolean;
  private serialNumbersSubject = new Subject<string>();
  private serialNumbersSubscription: Subscription;
  private metersSubject = new BehaviorSubject<Meter[]>([]);
  public meters = this.metersSubject.asObservable();

  constructor(
    private dialogRef: MatDialogRef<ClientDialogComponent>,
    private clientsService: ClientsService,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) client: Client,
    private metersService: MetersService
  ) {
    this.client = Client.cloned(client);
    this.newClient = (this.client.id === null);
    this.loading = false;
    this.serialNumbersSubscription = this.serialNumbersSubject
      .asObservable()
      .pipe(
        debounceTime(300)
      )
      .subscribe(serialNumber => this.findMeters(serialNumber));
  }


  ngOnInit() {
    this.serialNumbersSubject.next('');
  }

  ngOnDestroy(): void {
    this.metersSubject.complete();
    this.serialNumbersSubscription.unsubscribe();
  }

  save() {
    this.loading = true;
    this.clientsService.saveClient(this.client)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe(
        client => this.handleSuccess(client),
        errorResponse => this.handleError(errorResponse)
      );
  }

  private handleSuccess(client: Client): void {
    this.notificationService.success(this.newClient ? 'A new client has been successfully registered.' : 'A client has been successfully updated.');
    return this.dialogRef.close(client);
  }

  private handleError(errorResponse: any): void {
    return this.notificationService.error(errorResponse.error.errorMessage);
  }

  cancel() {
    this.dialogRef.close(null);
  }

  meterAsString(meter?: Meter): string {
    if (meter) {
      return meter.serialNumber;
    }
    return '';
  }

  discardNonExistingSerialNumber() {
    if (this.client.meter && !this.client.meter.id) {
      this.client.meter = null;
    }
  }

  serialNumberChanged(serialNumber: string) {
    this.serialNumbersSubject.next(serialNumber);
  }

  private findMeters(serialNumber: string): void {
    this.loading = true;
    this.metersService.findMeters(new MetersSearchCriteria(serialNumber), new PageDefinition('serialNumber', 'asc', 0, 10))
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading = false)
      )
      .subscribe((page: Page<Meter>) => this.metersSubject.next(page.content));
  }
}
