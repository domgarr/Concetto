import { Component, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Concept } from '../models/concept';
import { Subject } from '../models/subject';
import { ConceptService } from '../services/concept.service';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { SubjectService } from '../services/subject.service';

@Component({
  selector: 'app-add-concept',
  templateUrl: './add-concept.component.html',
  styleUrls: ['./add-concept.component.css'],
  exportAs: 'conceptForm'
})
export class AddConceptComponent implements OnInit {
  concept : Concept;

  selectedSubject : Subject;
  subjects : Subject[];
  
  error : boolean;
  adding : boolean;
  updating : boolean;

  @ViewChild('conceptForm') conceptForm: any;

  nameReqError : string = "name is required."
  nameMinError : string = "name must be atleast one character long."; 
  nameMaxError : string = "name must not exceed 255 characters."; 

  explanationMinError : string = "Explanation must be atleast one character long."; 
  explanationMaxError : string = "Explanation ame must not exceed 255 characters."; 

  showMessage : boolean = false;
  showMessageContent : string = "test";

  constructor(private conceptService : ConceptService, private subjectService : SubjectService, private activatedRoute : ActivatedRoute, private cdr : ChangeDetectorRef, private router : Router) { 
    this.concept = new Concept();
    this.concept.subject = new Subject(); //Prevent error upon first render.
    
    this.error = false;
    this.adding = false;
    this.updating = false;
  }

  ngOnInit() {
    this.subjectService.getAllSubjects().subscribe(subjects =>{
      this.subjects = subjects;
      this.activatedRoute.paramMap.subscribe( params =>{
         if(params.has("subject_id")){
          let subjectId : string = params.get("subject_id");
          this.setSubjectSelectedTrue(subjectId);
          this.adding = true;
         }else if(params.has("concept_id")){
           let conceptId : string = params.get("concept_id");
           this.conceptService.getConceptById(Number(conceptId)).subscribe(concept =>{
           this.concept = concept;
           this.updating = true;
           });
         }else{
           subjects[0].selected = true;
           this.concept.subject = {...subjects[0]};
           this.selectedSubject = {...subjects[0]};
           this.adding = true;
         }
      });
  });

  }

  onConceptSubmit(isDone : boolean){
    let actionMessage : string;
    if(isDone){
      actionMessage = "'" + this.concept.name + "' is chuncked. Make sure to review regularly.";
    }else{
      actionMessage = "' has been saved. You may complete it at a later time.";
    }

    this.conceptService.addConcept(this.concept, isDone).subscribe((response) =>{
      this.showMessageContent = "'" + this.concept.name + actionMessage;
      this.showMessage = true;
      this.conceptForm.reset();
      /* After resetting the form, the subject.id will be set to null. Then we reinstantiate the subject.id using the
      cached value (selectedSubject.id). 

      Since the model changed, the view should udpate. Note we are using two-way binding in this example.
      Though for some reason, when I changed the model by setting the subject.id, no change was noticed in the view. To solve
      this problemn for the time being. I force the view to update after resetting the form and once more after settting subject.id.
      */
      this.cdr.detectChanges(); 
      this.concept.subject.id = this.selectedSubject.id;

      this.cdr.detectChanges();
    },
    err =>{
      console.log(err);
    });
  }

  onConceptUpdate(){
    this.conceptService.updateConcept(this.concept).subscribe( (updatedConcept : Concept) =>{
      console.log(updatedConcept);
      this.router.navigate(['/u/study', updatedConcept.subject.id]);
    });
  }

  setSubjectSelectedTrue(subjectId : string){
    if(subjectId.length != 0){
      let subjectIdNum = Number(subjectId);
      this.subjects.forEach( subject =>{
        if(subject.id === subjectIdNum){
          //Because conceptForm.reset will nullify all form values (2-way binding), making copies of the object is necessary.
          this.selectedSubject = {...subject};
          this.concept.subject = {...subject};
          subject.selected = true;
        }
      });
    }
  }

  onOptionChange(event){
    console.log(this.subjects);
    let subjectIdSelected = Number(event.target.value);  
    this.subjects.forEach(subject =>{
        if(subject.id === subjectIdSelected){
          subject.selected = true;
          this.selectedSubject = {...subject};
          this.concept.subject = {...subject};
        }else{
          subject.selected = false;
        }
    });
  }

  
}
