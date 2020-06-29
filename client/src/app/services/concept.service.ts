import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Concept} from '../models/concept';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConceptService {
  private readonly BASE_CONCEPT_URL = "/proxy/api/v1/concept/";
  private readonly getAllConceptsUrl = this.BASE_CONCEPT_URL + "all";
  private readonly GET_ALL_CONCEPTS_BY_SUBJECT_ID = this.BASE_CONCEPT_URL + "subject/";
  private readonly SCHEDULED_PARAMETER : string = "is_scheduled";

  constructor(private http : HttpClient) { }

  addConcept(concept : Concept, isDone : boolean) : Observable<Concept> {
    let httpOptions = this.getHttpOptions();
    httpOptions.headers.set('observe', 'response');

    return this.http.put<Concept>(this.BASE_CONCEPT_URL, concept, this.getHttpOptions());
  }

  updateConcept(concept : Concept){
    let httpOptions = this.getHttpOptions();
    httpOptions.headers.set('observe', 'response');
    return this.http.patch<Concept>(this.BASE_CONCEPT_URL, concept, this.getHttpOptions());
  }

  getConceptById(id : number) : Observable<Concept> {
    return this.http.get<Concept>(this.BASE_CONCEPT_URL + id, this.getHttpOptions());
  }

  getConcepts() : Observable<Concept[]> {
    return this.http.get<Concept[]>(this.getAllConceptsUrl, this.getHttpOptions());
  }

  getAllConceptsBySubjectId(id : number){
    return this.http.get<Concept[]>(this.GET_ALL_CONCEPTS_BY_SUBJECT_ID + id, this.getHttpOptions());
  }

  //TODO: Figure out how to use a variable for 'is_scheduled' parameter
  getAllConceptsBySubjectIdScheduledForReview(id : number){
    return this.http.get<Concept[]>(this.GET_ALL_CONCEPTS_BY_SUBJECT_ID + id, {params: { "is_scheduled" : "true" }} );
  }

  private getHttpOptions(){
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return httpOptions;
  }
}
