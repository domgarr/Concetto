import { Component, OnInit } from '@angular/core';
import { Concept } from '../models/concept';
import { NgForm, NgModel } from '@angular/forms';
import { ConceptService } from '../services/concept.service';

@Component({
  selector: 'app-add-concept',
  templateUrl: './add-concept.component.html',
  styleUrls: ['./add-concept.component.css'],
  exportAs: 'conceptForm'
})
export class AddConceptComponent implements OnInit {

  concept : Concept;

  constructor(private conceptService : ConceptService) { 
    this.concept = new Concept();
  }

  ngOnInit() {
  }

  onConceptDone(){
    this.conceptService.addConcept(this.concept).subscribe((response) => {
        console.log(response);
    });
  }

}
