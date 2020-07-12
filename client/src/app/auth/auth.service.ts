import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  loggedIn = false;
  redirectUrl : string;

  private loginStatusSource = new Subject<boolean>();
  loginStatus$ = this.loginStatusSource.asObservable();

  private userUrl : string = "/proxy/user";
  private LOGOUT_URL : string = "/proxy/logout";
  private facebookLoginUrl : string = "/proxy/login/facebook";
  private googleLoginUrl : string = "/proxy/login/google";


  constructor(private http : HttpClient) { }

  isLoggedIn() : Observable<any> {
    return this.http.post(this.userUrl,{});
  }

  login(): Observable<any> {
    return this.http.get(this.googleLoginUrl);
  }

  logout(): Observable<any> {
    return this.http.post(this.LOGOUT_URL, {});
  }

  sendLoginStatus(status : boolean){
    this.loggedIn = status;
    this.loginStatusSource.next(status);
  }
}
