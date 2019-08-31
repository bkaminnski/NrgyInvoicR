import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, interval, of } from 'rxjs';
import { map, tap, catchError } from 'rxjs/operators';
import { AuthenticationRequest } from './model/authentication-request.model';
import { AuthenticationResponse } from './model/authentication-response.model';
import { AuthenticatedUser } from './model/authenticated-user.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private authenticatedUser: AuthenticatedUser;

  constructor(private http: HttpClient, private router: Router) {
    interval(30000).subscribe(() => this.refreshToken());
    this.restore();
  }

  private restore() {
    const authenticationToken = localStorage.getItem('authenticationToken');
    if (authenticationToken == null) {
      return;
    }
    try {
      const authenticatedUser = new AuthenticatedUser(authenticationToken);
      if (authenticatedUser.isTokenExpired()) {
        return;
      }
      this.authenticatedUser = authenticatedUser;
    } catch (e) {
      return;
    }
  }

  private refreshToken(): void {
    if (!this.isAuthenticated()) {
      this.forgetAndRedirect();
      return;
    }
    this.http.post<AuthenticationResponse>('/api/users/token', '')
      .pipe(
        map(authenticationResponse => authenticationResponse.authenticationToken)
      ).subscribe(
        authenticationToken => this.keep(authenticationToken),
        () => this.forgetAndRedirect()
      );
  }

  authenticate(authenticationRequest: AuthenticationRequest): Observable<string> {
    return this.http.post<AuthenticationResponse>('/api/users/authentication', authenticationRequest)
      .pipe(
        map(authenticationResponse => authenticationResponse.authenticationToken),
        tap(authenticationToken => this.keep(authenticationToken))
      );
  }

  private keep(authenticationToken: string) {
    localStorage.setItem('authenticationToken', authenticationToken);
    this.authenticatedUser = new AuthenticatedUser(authenticationToken);
  }

  isAuthenticated(): boolean {
    return this.authenticatedUser != null && !this.authenticatedUser.isTokenExpired();
  }

  forget() {
    localStorage.removeItem('authenticationToken');
    this.authenticatedUser = null;
  }

  forgetAndReroute(redirectUrl: string = null) {
    this.forget();
    if (!this.onLoginPage()) {
      this.router.navigate(['/login'], { queryParams: { redirectUrl: redirectUrl } });
    }
  }

  forgetAndRedirect() {
    this.forget();
    if (!this.onLoginPage()) {
      window.location.href = '/login';
    }
  }

  private onLoginPage(): boolean {
    return this.router.url.startsWith('/login');
  }

  get authenticatedUserToken() {
    return this.authenticatedUser ? this.authenticatedUser.authenticationToken : '';
  }
}
