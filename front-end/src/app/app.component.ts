import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'concetto';
  loggedIn : boolean;

  constructor(private authService : AuthService, private router : Router){
    this.loggedIn = false;
    //Parse URL for the case that Authentication is succesfull.
    let currentUrl = window.location.href;
    let originUrl = window.location.origin;
    let redirectUrl = currentUrl.slice(originUrl.length, currentUrl.length);

    //edge case for /home
    if( redirectUrl.localeCompare("/home") == 0 || redirectUrl.localeCompare("/") == 0){
      redirectUrl = "/u"
    }
    
    this.authService.isLoggedIn().subscribe(
    response => this.loginSuccess(),
    error => {
      this.loginFailure()
    }
   ).add(()=>{
    if(this.authService.isLoggedIn){
      //this.authService.redirectUrl has a value if the user was unauthenticated when visting a secured URL.
      let redirect = this.authService.redirectUrl ? this.router.parseUrl(this.authService.redirectUrl) : redirectUrl;
      // Redirect the user
      this.router.navigateByUrl(redirect);
    }else{
      this.router.initialNavigation();
    }
  });
}

ngOnInit(){
}

loginSuccess() : void{
  this.authService.loggedIn = true;
  this.authService.sendLoginStatus(true);
}

loginFailure() : void{    
  this.authService.loggedIn = false;
}

isLoggedIn() : boolean {
  return this.authService.loggedIn;
}
}
