import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import {Concept} from '../models/concept';
import {Observable, of} from 'rxjs';


enum SortParam {
  SAVED = "saved",
  IS_SCHEDULED = "is_scheduled"
}

@Injectable({
  providedIn: 'root'
})
export class ConceptService {
  private readonly BASE_CONCEPT_URL = "/proxy/api/v1/concept/";
  private readonly getAllConceptsUrl = this.BASE_CONCEPT_URL + "all";
  private readonly GET_ALL_CONCEPTS_BY_SUBJECT_ID = this.BASE_CONCEPT_URL + "subject/";
  private readonly SCHEDULED_PARAMETER : string = "is_scheduled";
  //https://stackoverflow.com/questions/29844959/enum-inside-class-typescript-definition-file
  static readonly SortParam = SortParam;
  

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

  //TODO: Figure out how to use a variable for 'is_scheduled' parameter
  getAllConceptsBySubjectId(id : string, sortParam : SortParam){
    let params : HttpParams = new HttpParams();  
    params = params.set("s", sortParam);
    

    console.log(params);

    return this.http.get<Concept[]>(this.GET_ALL_CONCEPTS_BY_SUBJECT_ID + id, { params } );
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
