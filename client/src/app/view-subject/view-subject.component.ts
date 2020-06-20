import { Component, OnInit } from '@angular/core';
import {Subject} from '../models/subject';
import { SubjectService } from '../services/subject.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-view-subject',
  templateUrl: './view-subject.component.html',
  styleUrls: ['./view-subject.component.css']
})
export class ViewSubjectComponent implements OnInit {

  constructor(private subjectService : SubjectService, private router : Router) { }

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
    this.router.navigate(['/u/add-concept', subjectId]);
  }

  onStudySubject(subjectId : number){
    this.router.navigate(['/u/study', subjectId]);
  }



}
