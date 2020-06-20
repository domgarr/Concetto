import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from './user.component';
import { AddConceptComponent } from '../add-concept/add-concept.component';
import { AuthGuard } from '../auth/auth.guard';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ViewConceptComponent } from '../view-concept/view-concept.component';
import { ViewSubjectComponent } from '../view-subject/view-subject.component';
import { StudyComponent } from '../study/study.component';

const routes: Routes = [
  {
  path: 'u',
  component: UserComponent,
  canActivate: [AuthGuard],
  children: [
    {
    path : '', 
    children: [
        {path: 'add-concept', component: AddConceptComponent},
        {path: 'add-concept/:subject_id', component: AddConceptComponent},
        {path: 'study/:subject_id', component: StudyComponent},
        {path: 'view-subject', component: ViewSubjectComponent},

        {path:'', component: DashboardComponent}
      ]
    }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { 
  
}
