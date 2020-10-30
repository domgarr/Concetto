import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  loggedIn = false;
  redirectUrl : string;

  private loginStatusSource = new Subject<boolean>();
  loginStatus$ = this.loginStatusSource.asObservable();

  private readonly BASE_URL = environment.baseUrl;


  private readonly userUrl : string = this.BASE_URL + "/user";
  private readonly LOGOUT_URL : string = this.BASE_URL +"/logout";
  private readonly facebookLoginUrl : string = this.BASE_URL +"/login/facebook";
  private readonly googleLoginUrl : string = this.BASE_URL +"oauth2/authorization/google";


  constructor(private http : HttpClient) { }

  isLoggedIn() : Observable<any> {
    return this.http.get(this.userUrl,{});
  }

  login(): Observable<any> {
    return this.http.get(this.googleLoginUrl, {observe : "response"});
  }

  logout(): Observable<any> {
    return this.http.post(this.LOGOUT_URL, {});
  }

  sendLoginStatus(status : boolean){
    this.loggedIn = status;
    this.loginStatusSource.next(status);
  }
  
}
