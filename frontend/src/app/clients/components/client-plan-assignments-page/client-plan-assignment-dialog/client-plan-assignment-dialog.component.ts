import { Component, OnInit, Inject } from '@angular/core';
import { finalize } from 'rxjs/operators';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Client } from 'src/app/clients/model/client.model';
import { ClientPlanAssignment } from 'src/app/clients/model/client-plan-assignment.model';
import { ClientPlanAssignmentsService } from '../client-plan-assignments.service';
import { NotificationService } from 'src/app/core/components/notification/notification.service';

@Component({
  templateUrl: './client-plan-assignment-dialog.component.html',
  styleUrls: ['./client-plan-assignment-dialog.component.scss'],
  providers: [
    ClientPlanAssignmentsService
  ]
})
export class ClientPlanAssignmentDialogComponent implements OnInit {
  public loading: boolean;
  private client: Client;
  public clientPlanAssignment: ClientPlanAssignment;

  constructor(
    private dialogRef: MatDialogRef<ClientPlanAssignmentDialogComponent>,
    private clientPlanAssignmentsService: ClientPlanAssignmentsService,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) data: { client: Client, clientPlanAssignment: ClientPlanAssignment }
  ) {
    console.log(data.client);
    console.log(data.clientPlanAssignment);
    this.client = data.client;
    this.clientPlanAssignment = ClientPlanAssignment.cloned(data.clientPlanAssignment);
    this.loading = false;
  }

  ngOnInit() { }

  save() {
    this.loading = true;
    this.clientPlanAssignmentsService.saveClientPlanAssignment(this.client, this.clientPlanAssignment)
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe(
        clientPlanAssignment => this.handleSuccess(clientPlanAssignment),
        errorResponse => this.handleError(errorResponse)
      );
  }

  private handleSuccess(clientPlanAssignment: ClientPlanAssignment): void {
    this.notificationService.success(
      this.clientPlanAssignment.isNew() ? 'A new plan has been successfully assigned to the client.' : 'Plan assignment has been successfully updated.'
    );
    return this.dialogRef.close(clientPlanAssignment);
  }

  private handleError(errorResponse: any): void {
    return this.notificationService.error(errorResponse.error.errorMessage);
  }

  cancel() {
    this.dialogRef.close(null);
  }
}
