import { Component } from '@angular/core';
import { AuthService } from './auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'concetto';

  constructor(private authService : AuthService, private router : Router){
    //Parse URL for the case that Authentication is succesfull.
    let currentUrl = window.location.href;
    let originUrl = window.location.origin;
    let redirectUrl = currentUrl.slice(originUrl.length, currentUrl.length);

    console.log(redirectUrl);

    //edge case for /home
    if( redirectUrl.localeCompare("/home") == 0 || redirectUrl.localeCompare("/") == 0){
      redirectUrl = "/u"
    }
    
    this.authService.isLoggedIn().subscribe(
    response => this.loginSuccess(),
    error => this.loginFailure()
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

loginSuccess() : void{
  console.log('login sucessfull;');
  this.authService.loggedIn = true;
}

loginFailure() : void{    
  console.log('login unsucessfull;');
  this.authService.loggedIn = false;
}
}
