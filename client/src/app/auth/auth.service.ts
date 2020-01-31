import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  loggedIn = false;
  redirectUrl : string;

  private userUrl : string = "/api/user";
  private facebookLoginUrl : string = "/api/login/facebook";
  private googleLoginUrl : string = "/api/login/google";

  constructor(private http : HttpClient) { }
 
  isLoggedIn() : Observable<any> {
    return this.http.post(this.userUrl,{});
  }

  login(): Observable<any> {
    return this.http.get(this.googleLoginUrl);
  }

  logout(): void {
    this.loggedIn = false;
  }
}
