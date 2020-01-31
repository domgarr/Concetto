import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { AddConceptComponent } from '../add-concept/add-concept.component';


@NgModule({
  declarations: [
    UserComponent,
    DashboardComponent,
    AddConceptComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule
  ]
})
export class UserModule { }
