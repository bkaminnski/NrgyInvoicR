import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthenticationService } from './authentication.service';
import { filter, tap, catchError } from 'rxjs/operators';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.authenticationService.authenticatedUserToken}`
      }
    });
    return next.handle(request)
      .pipe(
        tap(
          () => { },
          (error: any) => {
            if (error instanceof HttpErrorResponse && error.status === 401) {
              this.authenticationService.forgetAndReroute();
            }
          }
        )
      );
  }
}
