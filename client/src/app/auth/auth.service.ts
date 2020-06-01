import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  loggedIn = false;
  redirectUrl : string;

  private userUrl : string = "/proxy/user";
  private facebookLoginUrl : string = "/login/facebook";
  private googleLoginUrl : string = "/login/google";

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
