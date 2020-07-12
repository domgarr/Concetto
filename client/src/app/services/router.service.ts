import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouterService {

  constructor(private router : Router) { }

  /**
   * 
   * @param subjectId : Can be either a string or an integer. If it's an integer, it will be converted to a string.
   */
  routeToStudy(subjectId ){
    this.router.navigate(['/u/study', subjectId]);
   }

  routeToAddConcept(subjectId ) {
    this.router.navigate(['/u/add', subjectId]);
  }

  routeToFinishConcept(subjectId ){
    this.router.navigate(['/u/finish', subjectId]);
  }

  routeToEditConcept(conceptId){
    this.router.navigate(['/u/add-concept', conceptId]);
  }

  routeToDashbaord(){
    this.router.navigate(['/u']);
  }

  routerToLogin(){
    this.router.navigate(['/']);
  }
  
}
