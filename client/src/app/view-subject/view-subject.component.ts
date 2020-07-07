import { Component, OnInit } from '@angular/core';
import {Subject} from '../models/subject';
import { SubjectService } from '../services/subject.service';
import { DatePipe } from '@angular/common';
import { RouterService } from '../services/router.service';


@Component({
  selector: 'app-view-subject',
  templateUrl: './view-subject.component.html',
  styleUrls: ['./view-subject.component.css']
})
export class ViewSubjectComponent implements OnInit {

  constructor(private subjectService : SubjectService,private routerService : RouterService, public datePipe: DatePipe) { }

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
