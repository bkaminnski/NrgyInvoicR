import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthenticationRequest } from './model/authentication-request.model';
import { AuthenticationResponse } from './model/authentication-response.model';
import { AuthenticatedUser } from './model/authenticated-user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private authenticatedUser: AuthenticatedUser;

  constructor(private http: HttpClient) {
    this.restore();
  }

  private restore() {
    const authenticationToken = localStorage.getItem('authenticationToken');
    if (authenticationToken == null) {
      return;
    }
    const authenticatedUser = new AuthenticatedUser(authenticationToken);
    if (authenticatedUser.isTokenExpired()) {
      return;
    }
    this.authenticatedUser = authenticatedUser;
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

  get authenticatedUserToken() {
    return this.authenticatedUser ? this.authenticatedUser.authenticationToken : '';
  }
}
