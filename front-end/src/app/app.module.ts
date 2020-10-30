import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AddConceptComponent } from './add-concept/add-concept.component';
import { LoginComponent } from './login/login.component';

import { AppRoutingModule }        from './app-routing.module';

import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { UserModule } from './user/user.module';
import { DatePipe } from '@angular/common';
import { EditSubjectComponent } from './edit-subject/edit-subject.component';

import { HTTP_INTERCEPTORS } from '@angular/common/http';

import {LoginInterceptor} from './login/login.interceptor';

/** Http interceptor providers in outside-in order */
export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: LoginInterceptor, multi: true },
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    PageNotFoundComponent,
    EditSubjectComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    UserModule,
    AppRoutingModule,
    HttpClientModule,
    HttpClientXsrfModule
  ],
  providers: [DatePipe, httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
