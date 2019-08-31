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
    return this.authenticationService.isAuthenticated();
  }

  logOut() {
    this.authenticationService.forgetAndReroute();
  }
}
