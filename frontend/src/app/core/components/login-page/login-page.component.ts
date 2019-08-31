import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthenticationRequest } from '../../model/authentication-request.model';
import { AuthenticationService } from '../../authentication.service';
import { NotificationService } from '../notification/notification.service';

@Component({
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  private redirectUrl: string;
  public authenticationRequest: AuthenticationRequest;

  constructor(private route: ActivatedRoute, private router: Router, private authenticationService: AuthenticationService, private notificationService: NotificationService) {
  }

  ngOnInit() {
    this.redirectUrl = this.route.snapshot.queryParams['redirectUrl'];
    this.authenticationRequest = new AuthenticationRequest('', '');
    if (this.authenticationService.isAuthenticated()) {
      this.navigateAfterLogin();
    }
  }

  private navigateAfterLogin() {
    this.router.navigate([this.redirectUrl || '/']);
  }

  logIn() {
    this.authenticationService.authenticate(this.authenticationRequest).subscribe(
      authenticationToken => authenticationToken ? this.navigateAfterLogin() : this.showErrorMessage(),
      () => this.showErrorMessage()
    );
  }

  private showErrorMessage() {
    this.notificationService.error('Wrong email address or password.');
  }
}
