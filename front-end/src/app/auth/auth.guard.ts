import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService }      from './auth.service';
import { Router }      from '@angular/router';


@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router : Router){
    
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot):  boolean {
    let url : string = state.url;

    return this.checkLogin(url);
  }

  checkLogin(url : string) : boolean {
    
    if(this.authService.loggedIn) {return true;}
    
    this.authService.redirectUrl = url;
    this.router.navigate(['/home']);

    return false;
  }
}
