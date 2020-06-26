import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { InterInterval } from '../models/interInteval';
import {Observable, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InterIntervalService {
  private readonly INTER_INTERVAL_BASE_URL = "proxy/api/v1/interval"
  private readonly CALCULATE = "/calculate";

  constructor(private http : HttpClient) { }

  calculateNextInterInterval(interInterval : InterInterval) : Observable<InterInterval> {
    let httpOptions = {
      headers:  new HttpHeaders ({
        'Content-Type':  'application/json',
        'observe' : 'response'
      })
    };

    return this.http.post<InterInterval>(this.INTER_INTERVAL_BASE_URL + this.CALCULATE, interInterval, httpOptions);
  }

}
