import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { Concept } from '../models/concept';
import { ConceptService } from '../services/concept.service';
import { ActivatedRoute } from '@angular/router';
import { SubjectService } from '../services/subject.service';
import { Subject } from '../models/subject';

@Component({
  selector: 'app-study',
  templateUrl: './study.component.html',
  styleUrls: ['./study.component.css']
})
export class StudyComponent implements OnInit {

concept : Concept;
subject : Subject;
private index : number;
concepts : Concept[];
  

  constructor(private conceptService : ConceptService, private subjectService : SubjectService, private activatedRoute : ActivatedRoute) {
    this.index = 0;
   }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params =>{
      let subjectId : number = Number(params.get("subject_id"));
      this.conceptService.getAllConceptsBySubjectId(subjectId).subscribe(concepts =>{
        this.concepts = concepts;
        this.concept = concepts[this.index];
      });
      this.subjectService.getSubject(subjectId).subscribe(subject =>{
        this.subject = subject;
        console.log(this.subject);
      });
    });
  }

  onGoodPressed(){
    this.index++;
    if(this.index < this.concepts.length){
    this.concept = this.concepts[this.index];
    console.log(this.index);
    }
  }
}
