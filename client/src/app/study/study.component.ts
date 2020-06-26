import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { Concept } from '../models/concept';
import { ConceptService } from '../services/concept.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectService } from '../services/subject.service';
import { Subject } from '../models/subject';
import { InterIntervalService } from '../services/inter-interval.service';

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

private readonly GOOD_RESPONSE_VALUE = 3;
private readonly PERFECT_RESPONSE_VALUE = 5;
  

  constructor(private conceptService : ConceptService, private subjectService : SubjectService, private activatedRoute : ActivatedRoute, private router : Router, private interIntervalService : InterIntervalService) {
    this.index = 0;
    this.subject = new Subject();
   }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params =>{
      let subjectId : number = Number(params.get("subject_id"));
      //TODO: Load in data using AuthGuard?
      this.conceptService.getAllConceptsBySubjectIdScheduledForReview(subjectId).subscribe(concepts =>{
        console.log(concepts);
        this.concepts = concepts;
        this.concept = concepts[this.index];
      });
      this.subjectService.getSubject(subjectId).subscribe(subject =>{
        this.subject = subject;
      });
    });
  }

  onGoodButtonPressed(event){
    this.responseButtonPressed(this.GOOD_RESPONSE_VALUE);
  }

  onPerfectButtonPressed($event){
    this.responseButtonPressed(this.PERFECT_RESPONSE_VALUE);
  }

  onAddButtonPress(){
    this.router.navigate(['/u/add-concept', this.subject.id]);
  }


  responseButtonPressed(responseValue){
    this.concept.interInterval.responseRating = responseValue;
    this.interIntervalService.calculateNextInterInterval(this.concept.interInterval).subscribe(interInterval =>{
      this.concept.interInterval = interInterval;
      this.nextConcept();
    });
  }

  nextConcept(){
    this.index++;
    if(this.index < this.concepts.length){
      this.concept = this.concepts[this.index];
    }
  }
}
