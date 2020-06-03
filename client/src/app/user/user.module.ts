import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { AddConceptComponent } from '../add-concept/add-concept.component';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';


@NgModule({
  declarations: [
    UserComponent,
    DashboardComponent,
    AddConceptComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule 
  ]
})
export class UserModule { }
