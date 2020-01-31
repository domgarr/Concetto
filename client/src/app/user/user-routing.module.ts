import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { UserComponent } from './user.component';
import { AddConceptComponent } from '../add-concept/add-concept.component';
import { AuthGuard } from '../auth/auth.guard';
import { DashboardComponent } from '../dashboard/dashboard.component';


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
export class UserRoutingModule { }
