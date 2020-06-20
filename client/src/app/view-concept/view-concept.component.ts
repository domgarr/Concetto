import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {Concept} from '../models/concept';
import { ConceptService } from '../services/concept.service';
declare var $: any;

@Component({
  selector: 'app-view-concept',
  templateUrl: './view-concept.component.html',
  styleUrls: ['./view-concept.component.css']
})
export class ViewConceptComponent implements OnInit {
  @Input() 
  concept : Concept;
  @Output() goodPressed = new EventEmitter<boolean>();
  
  isShowButtonVisible : boolean;

  constructor(private conceptService : ConceptService) { 
    this.isShowButtonVisible = true;
  }

  ngOnInit() {
    this.concept = new Concept();
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
