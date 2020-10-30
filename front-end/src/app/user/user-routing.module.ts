import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from './user.component';
import { AddConceptComponent } from '../add-concept/add-concept.component';
import { AuthGuard } from '../auth/auth.guard';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ViewSubjectComponent } from '../view-subject/view-subject.component';
import { StudyComponent } from '../study/study.component';
import { EditSubjectComponent } from '../edit-subject/edit-subject.component';

const routes: Routes = [
  {
  path: 'u',
  component: UserComponent,
  canActivate: [AuthGuard],
  children: [
    {
    path : '', 
    children: [
        {path: 'add', component: AddConceptComponent},
        {path: 'add/:subject_id', component: AddConceptComponent},
        {path: 'finish/:subject_id', component: AddConceptComponent},
        {path: 'edit/:concept_id', component: AddConceptComponent},
        {path:'edit/subject/:subject_id', component : EditSubjectComponent},
        {path: 'study/:subject_id', component: StudyComponent},
        {path: 'subject', component: ViewSubjectComponent},
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
