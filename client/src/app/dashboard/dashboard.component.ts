import { Component, OnInit } from '@angular/core';
import { SubjectService } from '../services/subject.service';
import { Subject } from '../models/subject';
import { RouterService } from '../services/router.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {

  subjectsToReview : Subject[];
  subjectsToFinish : Subject[];

  constructor(private subjectService : SubjectService, private routerService : RouterService ) { }

  ngOnInit() {
    this.subjectService.getAllSubjectsInDoneState().subscribe( subjects =>{
      this.subjectsToReview = subjects;
    });
    this.subjectService.getAllSubjectsInSaveState().subscribe(subjects =>{
      this.subjectsToFinish = subjects;
    });
  }

  onNeedToReviewClicked(subjectId : number){
    this.routerService.routeToStudy(subjectId);
  }

}
