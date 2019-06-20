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

  public info(message: string) {
    this.open(message, 'info', 'info');
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
      horizontalPosition: 'center',
      duration: Math.max(2000, message.length * 90)
    });
  }
}
