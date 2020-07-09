import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouterService {

  constructor(private router : Router) { }

  routeToStudy(subjectId : number){
    this.router.navigate(['/u/study', subjectId]);
   }

  routeToAddConcept(subjectId : number) {
    this.router.navigate(['/u/add', subjectId]);
  }

  routeToFinishConcept(subjectId : number){
    this.router.navigate(['/u/finish', subjectId]);
  }
}
