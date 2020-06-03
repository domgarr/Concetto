import { Component, OnInit, ViewChild } from '@angular/core';
import { Concept } from '../models/concept';
import { ConceptService } from '../services/concept.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-concept',
  templateUrl: './add-concept.component.html',
  styleUrls: ['./add-concept.component.css'],
  exportAs: 'conceptForm'
})
export class AddConceptComponent implements OnInit {
  concept : Concept;

  error : boolean;

  @ViewChild('conceptForm', {static: false}) conceptForm: any;

  nameReqError : string = "Name is required."
  nameMinError : string = "Name must be atleast one character long."; 
  nameMaxError : string = "Name must not exceed 255 characters."; 

  explanationMinError : string = "Explanation must be atleast one character long."; 
  explanationMaxError : string = "Explanation ame must not exceed 255 characters."; 

  showMessage : boolean = false;
  showMessageContent : string = "test";

  constructor(private conceptService : ConceptService, private router : Router) { 
    this.concept = new Concept();
    this.error = false;
  }

  ngOnInit() {
   
  }

  onConceptDone(){
    this.conceptService.addConcept(this.concept).subscribe((response) => {
      this.showMessageContent = "'" + this.concept.name + "' is chuncked. Make sure to review regularly.";
      this.showMessage = true;
      this.concept = new Concept();

      this.conceptForm.reset();
    });
    
  }

  onConceptSave(){
    this.conceptService.addConcept(this.concept).subscribe((response) =>{
      this.showMessageContent = "'" + this.concept.name + "' has been saved. You may complete it at a later time.";
      this.showMessage = true;
      this.concept = new Concept();

      this.conceptForm.reset();
    })
  }



}
