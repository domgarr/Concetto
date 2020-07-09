import { Component, OnInit } from '@angular/core';
import {Subject} from '../models/subject';
import { SubjectService } from '../services/subject.service';
import { DatePipe } from '@angular/common';
import { RouterService } from '../services/router.service';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-view-subject',
  templateUrl: './view-subject.component.html',
  styleUrls: ['./view-subject.component.css']
})
export class ViewSubjectComponent implements OnInit {
  showMessage : boolean;

  constructor(private subjectService : SubjectService,private routerService : RouterService, public datePipe: DatePipe, private router : Router) { 
    this.showMessage = false;
    
    let extras = this.router.getCurrentNavigation().extras;
    if(extras.state){
      this.showMessage = extras.state.finishedConcepts
    }
  }

  subjects : Subject[];

  ngOnInit() {
    this.subjectService.getAllSubjects().subscribe( subjects =>{
      this.subjects = subjects;
    });

  }

  onSubjectSaved(subject : Subject){
    this.subjects.push(subject);
  }

  onAddConceptToSubject(subjectId : number){
    this.routerService.routeToAddConcept(subjectId);
  }

  onStudySubject(subjectId : number){
    this.routerService.routeToStudy(subjectId);
  }
}
