import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes }  from '@angular/router';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth/auth.guard';

const appRoutes: Routes = [
  { path: 'home', component: LoginComponent },
  { path: '',   redirectTo: 'home', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
];


@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {initialNavigation: false, // the propery to delay navigationn
      enableTracing: true //For debugging
      }
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
