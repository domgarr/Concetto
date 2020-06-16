import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subject } from '../models/subject';

@Injectable({
  providedIn: 'root'
})
export class SubjectService {

  private readonly subjectURL = "/proxy/api/v1/subject"

  constructor(private http : HttpClient) { }

  getAllSubjects() : Observable<Subject[]>{
    return this.http.get<Subject[]>(this.subjectURL);
  }

  saveSubject(subject : Subject) : Observable<Subject> {
    let httpOptions = {
      headers:  new HttpHeaders ({
        'Content-Type':  'application/json',
        'observe' : 'response'
      })
    };
    
    return this.http.put<Subject>(this.subjectURL, subject, httpOptions);
  }
}
