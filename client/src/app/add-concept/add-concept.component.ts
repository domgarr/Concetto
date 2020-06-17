import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Concept } from '../models/concept';
import { Subject } from '../models/subject';
import { ConceptService } from '../services/concept.service';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { SubjectService } from '../services/subject.service';
import { Observable, forkJoin, empty } from 'rxjs';


@Component({
  selector: 'app-add-concept',
  templateUrl: './add-concept.component.html',
  styleUrls: ['./add-concept.component.css'],
  exportAs: 'conceptForm'
})
export class AddConceptComponent implements OnInit {
  concept : Concept;
  subjects : Subject[];
  selectedId : number;

  subjectSelected : number;
  error : boolean;

  @ViewChild('conceptForm', {static: false}) conceptForm: any;

  nameReqError : string = "name is required."
  nameMinError : string = "name must be atleast one character long."; 
  nameMaxError : string = "name must not exceed 255 characters."; 

  explanationMinError : string = "Explanation must be atleast one character long."; 
  explanationMaxError : string = "Explanation ame must not exceed 255 characters."; 

  showMessage : boolean = false;
  showMessageContent : string = "test";

  constructor(private conceptService : ConceptService, private subjectService : SubjectService, private activatedRoute : ActivatedRoute, private cdr : ChangeDetectorRef) { 
    this.concept = new Concept();
    this.error = false;
  }

  ngOnInit() {
   
    this.subjectService.getAllSubjects().subscribe(subjects =>{
      this.subjects = subjects;
      this.activatedRoute.paramMap.subscribe( params =>{
         if(params.has("subject_id")){
          let subjectId : string = params.get("subject_id");
          this.setSubjectSelectedTrue(subjectId);
         }
      })
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

  setSubjectSelectedTrue(subjectId : string){
    if(subjectId.length != 0){
      let subjectIdNum = Number(subjectId);
      this.subjects.forEach( subject =>{
        if(subject.id === subjectIdNum){
          subject.selected = true;
        }
      })
  }
  }



}
