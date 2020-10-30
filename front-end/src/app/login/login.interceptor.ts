import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {tap} from 'rxjs/operators';
import { AuthService } from '../auth/auth.service';
import { RouterService } from '../services/router.service';


@Injectable()
export class LoginInterceptor implements HttpInterceptor {

  constructor(private authService :AuthService, private routerService : RouterService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      tap(event =>{
      }, (error : HttpErrorResponse) =>{
        if(error.status == 401){
          //If a user attempts to access a resource they do not have access to or if there session expires,
          //the user will be directed to the login page.
          this.authService.loggedIn = false;
          this.routerService.routeToLogin();

        }
      })
    );
  }
}
