import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Concept} from '../models/concept';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConceptService {
  private readonly conceptUrl = "/proxy/api/v1/concepts"
  private readonly getAllConceptsUrl = "/proxy/api/v1/concepts/all"

  constructor(private http : HttpClient) { }

  addConcept(concept : Concept) : Observable<Concept> {
    let httpOptions = this.getHttpOptions();
    httpOptions.headers.set('observe', 'response');
    return this.http.put<Concept>(this.conceptUrl, concept, this.getHttpOptions());
  }

  getConcepts() : Observable<Concept[]> {
    return this.http.get<Concept[]>(this.getAllConceptsUrl, this.getHttpOptions());
  }

  getHttpOptions(){
    let httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      })
    };
    return httpOptions;
  }

  



}
