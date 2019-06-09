import { OnInit, Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material';
import { NotificationComponent } from './notification.component';

@Injectable({
  providedIn: 'root'
})
export class NotificationService implements OnInit {

  constructor(private snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  public success(message: string) {
    this.open(message, 'success', 'check_circle');
  }

  public error(message: string) {
    this.open(message, 'error', 'error');
  }

  private open(message: string, type: string, icon: string) {
    this.snackBar.openFromComponent(NotificationComponent, {
      data: {
        message: message,
        icon: icon
      },
      panelClass: type + '-notification-panel',
      verticalPosition: 'top',
      duration: 4000
    });
  }
}
