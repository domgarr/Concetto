import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import {Concept} from '../models/concept';
import { ConceptService } from '../services/concept.service';
declare var $: any;

@Component({
  selector: 'app-view-concept',
  templateUrl: './view-concept.component.html',
  styleUrls: ['./view-concept.component.css']
})
export class ViewConceptComponent implements OnChanges {
  @Input() 
  concept : Concept;
  @Output() goodPressed = new EventEmitter<boolean>();
  
  set Concept(concept: Concept) {
    this.concept = concept;
  };
  isShowButtonVisible : boolean;

  constructor(private conceptService : ConceptService) { 
    this.isShowButtonVisible = true;
  }

  ngOnChanges(changes){
    console.log(changes);
  }

  onClickShow(){
    this.isShowButtonVisible = false;
  }

  onGoodPressed(){
    this.goodPressed.emit();
    this.isShowButtonVisible = true;
    $('#explanation').collapse('hide')
  }

}
