import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Concept } from '../models/concept';
import { ConceptService } from '../services/concept.service';
import { SubjectService } from '../services/subject.service';
import { Subject } from '../models/subject';
import { RouterService } from '../services/router.service';



@Component({
  selector: 'app-edit-subject',
  templateUrl: './edit-subject.component.html',
  styleUrls: ['./edit-subject.component.css']
})
export class EditSubjectComponent implements OnInit {
  public concepts : Concept[];
  public subject : Subject;

  constructor(private activatedRoute : ActivatedRoute, private conceptService : ConceptService, private subjectService : SubjectService, private routerService : RouterService) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(paramMap =>{
      let subjectId = paramMap.get("subject_id");
      
      this.subjectService.getSubject(subjectId).subscribe(subject =>{
        this.subject = subject;
      })
      
      this.conceptService.getAllConceptsBySubjectId(subjectId, ConceptService.SortParam.ALL).subscribe(concepts =>{
        this.concepts = concepts;
      });
    });
  }

  onConceptUpdate(conceptId: number){
    this.routerService.routeToEditConcept(conceptId);
  }

  onConceptDelete(conceptId : number){
    this.conceptService.deleteConcept(conceptId).subscribe( response =>{

      this.removeConceptFromList(conceptId);
    });
  }

  removeConceptFromList(conceptId : number){
    let indexToDelete = this.concepts.findIndex( concept => concept.id == conceptId);
    //Splice will alter the original array.
    this.concepts.splice(indexToDelete, 1);
  
    this.subject.count--
  }

}
