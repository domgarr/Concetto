import { Component, OnInit, Input, ChangeDetectorRef } from '@angular/core';
import { Concept } from '../models/concept';
import { ConceptService } from '../services/concept.service';
import { ActivatedRoute, Router } from '@angular/router';
import { SubjectService } from '../services/subject.service';
import { Subject } from '../models/subject';
import { InterIntervalService } from '../services/inter-interval.service';
import { empty } from 'rxjs';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-study',
  templateUrl: './study.component.html',
  styleUrls: ['./study.component.css']
})
export class StudyComponent implements OnInit {

concept : Concept;
subject : Subject;

concepts : Concept[];

rehearsedConcepts : Concept[];
renderViewConcept : boolean;

private readonly INCORRECT_RESPONSE_VALUE : number = 1;
private readonly GOOD_RESPONSE_VALUE : number = 3;
private readonly PERFECT_RESPONSE_VALUE : number = 5;
private readonly STYLE_WIDTH : string = "width: ";
private readonly DEFAULT_WIDTH_PERC : string = "0%";
private readonly DEFAULT_ARIA_VALUE_NOW : string = "0";

total : number;
incorrect : number;
good : number;
perfect : number;


incorrectProgressStyle : string;
incorrectProgressAriaValueNow : string;
goodProgressStyle : string;
goodProgressAriaValueNow : string;
perfectProgressStyle : string;
perfectProgressAriaValueNow : string;
  

  constructor(private conceptService : ConceptService, private subjectService : SubjectService, private activatedRoute : ActivatedRoute, private router : Router, private interIntervalService : InterIntervalService, public datePipe : DatePipe) {
    this.renderViewConcept = true;
    this.subject = new Subject();

    this.rehearsedConcepts = new Array();

    this.incorrectProgressStyle = this.STYLE_WIDTH + this.DEFAULT_WIDTH_PERC; 
    this.incorrectProgressAriaValueNow = this.DEFAULT_ARIA_VALUE_NOW;

    this.goodProgressStyle = this.STYLE_WIDTH + this.DEFAULT_WIDTH_PERC; 
    this.goodProgressAriaValueNow = this.DEFAULT_ARIA_VALUE_NOW;

    this.perfectProgressStyle = this.STYLE_WIDTH + this.DEFAULT_WIDTH_PERC; 
    this.perfectProgressAriaValueNow = this.DEFAULT_ARIA_VALUE_NOW;
   }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params =>{
      let subjectId = params.get("subject_id");
      //TODO: Load in data using AuthGuard?
      this.conceptService.getAllConceptsBySubjectId(subjectId, ConceptService.SortParam.IS_SCHEDULED).subscribe(concepts =>{
        if(concepts.length == 0){
          this.renderViewConcept = false;
        }
        this.concepts = concepts;
        this.resetResponseRating();

        this.total = this.concepts.length;
        this.concept = this.concepts.shift();
      });
      this.subjectService.getSubject(subjectId).subscribe(subject =>{
        this.subject = subject;
      });
    });
  }

  onIncorrectButtonPressed(){
    this.responseButtonPressed(this.INCORRECT_RESPONSE_VALUE);
  }

  onGoodButtonPressed(){
    this.responseButtonPressed(this.GOOD_RESPONSE_VALUE);
  }

  onPerfectButtonPressed(){
    this.responseButtonPressed(this.PERFECT_RESPONSE_VALUE);
  }

  responseButtonPressed(responseValue){
    this.concept.interInterval.responseRating = responseValue;
    this.updateProgressBar();

    if(!this.isRehearsed(responseValue)){
      this.placeConceptAtEnd();
      return;
    }

    this.interIntervalService.calculateNextInterInterval(this.concept.interInterval).subscribe(interInterval =>{
      this.concept.interInterval = interInterval;
      if(this.concepts.length == 0){
        this.renderViewConcept = false;
        this.subjectService.getSubject(this.subject.id).subscribe(subject =>{
          this.subject = subject;
        });
        return;
      }
      this.rehearsedConcepts.push(this.concept);
      this.concept = this.concepts.shift();
    });
  }

  placeConceptAtEnd() {
    this.concepts.push(this.concept);
    this.concept = this.concepts.shift();
  }

  isRehearsed(responseValue){
    if(responseValue >= this.GOOD_RESPONSE_VALUE){
      return true;
    }
    return false; 
  }

  updateProgressBar(){
    this.initProgressCounters();

    //Need to include the single concept holder.
    this.updateProgressCounter(this.concept.interInterval.responseRating);

    this.concepts.forEach((concept)=>{
      this.updateProgressCounter(concept.interInterval.responseRating);
    });

    this.rehearsedConcepts.forEach((concept)=>{
      this.updateProgressCounter(concept.interInterval.responseRating);
    });

    let percentage = this.calculateProgressPerc(this.incorrect);
    this.incorrectProgressStyle = this.STYLE_WIDTH + percentage + "%"; 
    this.incorrectProgressAriaValueNow = percentage;

    percentage = this.calculateProgressPerc(this.good);
    this.goodProgressStyle = this.STYLE_WIDTH + percentage + "%";  
    this.goodProgressAriaValueNow = percentage;
    

    percentage = this.calculateProgressPerc(this.perfect);
    this.perfectProgressStyle = this.STYLE_WIDTH + percentage + "%";   
    this.perfectProgressAriaValueNow = percentage;
    
  }

  updateProgressCounter(responseValue){
    switch(responseValue){
      case this.INCORRECT_RESPONSE_VALUE:
        this.incorrect++;
        break;
      case this.GOOD_RESPONSE_VALUE:
        this.good++;
        break;
      case this.PERFECT_RESPONSE_VALUE:
        this.perfect++;
        break;
    }
  }

  initProgressCounters(){
    this.incorrect = 0;
    this.good = 0;
    this.perfect = 0;
  }

  calculateProgressPerc(counter : number) : string{
    return "" + ((counter / this.total) * 100);
  }

  resetResponseRating(){
    this.concepts.forEach((concept)=>{
       concept.interInterval.responseRating = 0;
    });
  }

  onAddButtonPress(){
    this.router.navigate(['/u/add-concept', this.subject.id]);
  }

  onEditButtonPress(){
    this.router.navigate(['/u/edit', this.concept.id]);

  }
}
