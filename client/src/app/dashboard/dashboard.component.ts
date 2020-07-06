import { Component, OnInit } from '@angular/core';
import { SubjectService } from '../services/subject.service';
import { Subject } from '../models/subject';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  subjectsToReview : Subject[];

  constructor(private subjectService : SubjectService) { }

  ngOnInit() {
    this.subjectService.getAllSubjectsToReview().subscribe( subjects =>{
      this.subjectsToReview = subjects;
    });
  }

}
