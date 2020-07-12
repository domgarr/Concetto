import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Subscription } from 'rxjs';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  loggedIn : boolean;
  loginStatusSubscription : Subscription;

  constructor(private routerService : RouterService, private authService : AuthService) {
    this.loggedIn = false;
   }
  

  ngOnInit() {
    this.loginStatusSubscription = this.authService.loginStatus$.subscribe(loginStatus =>{
      this.loggedIn = loginStatus;
    })
  }

  ngOnDestroy(){
    this.loginStatusSubscription.unsubscribe();
  }

  onLogout(){
    this.authService.logout().subscribe(response =>{
      this.routerService.routerToLogin();
    })
  }
}
