import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../authentication.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  constructor(private router: Router, private authenticationService: AuthenticationService) { }

  ngOnInit() {
  }

  userIsLoggedIn(): boolean {
    const authenticated = this.authenticationService.isAuthenticated();
    if (!authenticated && !this.router.url.startsWith('/login')) {
      this.logOut();
    }
    return authenticated;
  }

  logOut() {
    this.authenticationService.forget();
    this.router.navigate(['/login']);
  }
}
