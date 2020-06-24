import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { Concept } from '../models/concept';
import { ConceptService } from '../services/concept.service';
import { ActivatedRoute, Router } from '@angular/router';
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
  

  constructor(private conceptService : ConceptService, private subjectService : SubjectService, private activatedRoute : ActivatedRoute, private router : Router) {
    this.index = 0;
    this.subject = new Subject();
   }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params =>{
      let subjectId : number = Number(params.get("subject_id"));
      //TODO: Load in data using AuthGuard?
      this.conceptService.getAllConceptsBySubjectIdScheduledForReview(subjectId).subscribe(concepts =>{
        this.concepts = concepts;
        this.concept = concepts[this.index];
      });
      this.subjectService.getSubject(subjectId).subscribe(subject =>{
        this.subject = subject;
      });
    });
  }

  onGoodPressed(event){
    this.index++;
    if(this.index < this.concepts.length){
    this.concept = this.concepts[this.index];
    }
  }

  onAddButtonPress(){
    this.router.navigate(['/u/add-concept', this.subject.id]);
  }
}
