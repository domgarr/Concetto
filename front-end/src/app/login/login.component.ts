import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Router }      from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

 message :string;

  constructor(public authService : AuthService, private router: Router) { 

  }

  ngOnInit() {
    if(this.authService.isLoggedIn){  
      let redirect = this.authService.redirectUrl ? this.router.parseUrl(this.authService.redirectUrl) : '/u';
      this.router.navigateByUrl(redirect);
    }
  }

  googleLoginRedirect(){
    window.location.href="oauth2/authorization/google";
  }

  logout(){
    this.authService.logout();
  }

}
