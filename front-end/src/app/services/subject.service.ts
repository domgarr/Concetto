import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subject } from '../models/subject';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  private readonly BASE_URL = environment.baseUrl;

  private readonly SUBJECT_URL = this.BASE_URL + "/api/v1/subject"
  private readonly FIND_ALL_SUBJECTS = this.SUBJECT_URL + "/all";

  constructor(private http : HttpClient) { }

  getAllSubjects() : Observable<Subject[]>{
    return this.http.get<Subject[]>(this.FIND_ALL_SUBJECTS);
  }

  getAllSubjectsInDoneState() : Observable<Subject[]>{
    return this.http.get<Subject[]>(this.FIND_ALL_SUBJECTS, {params: { "s" : "done" }});
  }

  getAllSubjectsInSaveState() : Observable<Subject[]>{
    return this.http.get<Subject[]>(this.FIND_ALL_SUBJECTS, {params: { "s" : "save" }});
  }

  getSubject(id){
    return this.http.get<Subject>(this.SUBJECT_URL + "/" + id)
  }

  saveSubject(subject : Subject) : Observable<Subject> {
    let httpOptions = {
      headers:  new HttpHeaders ({
        'Content-Type':  'application/json',
        'observe' : 'response'
      })
    };
    
    return this.http.put<Subject>(this.SUBJECT_URL, subject, httpOptions);
  }
}
