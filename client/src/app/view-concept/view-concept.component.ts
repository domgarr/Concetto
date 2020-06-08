import { Component, OnInit } from '@angular/core';
import {Concept} from '../models/concept';
import { ConceptService } from '../services/concept.service';


@Component({
  selector: 'app-view-concept',
  templateUrl: './view-concept.component.html',
  styleUrls: ['./view-concept.component.css']
})
export class ViewConceptComponent implements OnInit {

  concepts : Concept[];

  constructor(private conceptService : ConceptService) { 

  }

  ngOnInit() {
    this.conceptService.getConcepts().subscribe(response =>{
      this.concepts = response.body.concepts;
    })
  }

}
