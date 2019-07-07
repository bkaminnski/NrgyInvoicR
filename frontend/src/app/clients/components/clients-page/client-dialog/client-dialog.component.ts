import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { finalize } from 'rxjs/operators';
import { Client } from 'src/app/clients/model/client.model';
import { ClientsService } from '../../clients.service';
import { NotificationService } from 'src/app/core/components/notification/notification.service';

@Component({
  templateUrl: './client-dialog.component.html',
  styleUrls: ['./client-dialog.component.scss'],
  providers: [
    ClientsService
  ]
})
export class ClientDialogComponent implements OnInit {
  public client: Client;
  public loading: boolean;

  constructor(
    private dialogRef: MatDialogRef<ClientDialogComponent>,
    private clientsService: ClientsService,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) client: Client
  ) {
    this.client = Client.cloned(client);
    this.loading = false;
  }

  ngOnInit() {
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
    this.notificationService.success(this.client.isNew() ? 'A new client has been successfully registered.' : 'A client has been successfully updated.');
    return this.dialogRef.close(client);
  }

  private handleError(errorResponse: any): void {
    return this.notificationService.error(errorResponse.error.errorMessage);
  }

  cancel() {
    this.dialogRef.close(null);
  }
}
