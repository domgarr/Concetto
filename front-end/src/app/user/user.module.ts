import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { AddConceptComponent } from '../add-concept/add-concept.component';
import { FormsModule, ReactiveFormsModule  } from '@angular/forms';
import { AddSubjectComponent } from '../add-subject/add-subject.component';
import { ViewSubjectComponent } from '../view-subject/view-subject.component';
import { ViewConceptComponent } from '../view-concept/view-concept.component';
import { StudyComponent } from '../study/study.component';


@NgModule({
  declarations: [
    UserComponent,
    DashboardComponent,
    AddConceptComponent,
    AddSubjectComponent,
    ViewSubjectComponent,
    ViewConceptComponent,
    StudyComponent
    ],
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule 
  ]
})
export class UserModule { }
