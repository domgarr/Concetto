import { Component, OnInit, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { SubjectService } from '../services/subject.service';
import { Subject } from '../models/subject';
import { RouterService } from '../services/router.service';

import Chart from 'chart.js';
import { ConceptService } from '../services/concept.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent implements OnInit {

  subjectsToReview : Subject[];
  subjectsToFinish : Subject[];

  chart : any;
  @ViewChild('reviewChart') reviewChartEl : ElementRef;
  data: Array<any>;
  maxY : number;

  constructor(private conceptService : ConceptService ,private subjectService : SubjectService, private routerService : RouterService, private cdr : ChangeDetectorRef) {
    
   }

  ngOnInit() {
    this.subjectService.getAllSubjectsInDoneState().subscribe( subjects =>{
      this.subjectsToReview = subjects;
    });
    this.subjectService.getAllSubjectsInSaveState().subscribe(subjects =>{
      this.subjectsToFinish = subjects;
    });

    this.data = new Array();
    this.maxY = 0;


    this.conceptService.getCountOfConceptsToReviewPerDate().subscribe( pairs =>{
      pairs.forEach( pair => {
        let obj = {};
        obj['x'] = new Date(pair.reviewDate);
        obj['y'] = pair.count;

        if(pair.count > this.maxY){
          this.maxY = pair.count;
        }

        this.data.push(obj);
      } );
      this.chart.update();
    });

  }

  ngAfterViewInit(){
    console.log("ngAfterViewInit");
    
    this.chart = new Chart(this.reviewChartEl.nativeElement,{
      type: 'line',
      data : this.getDataForChart(),
      options : this.getOptionsForChart()
     });
     
  }

  onNeedToReview(subjectId : number){
    this.routerService.routeToStudy(subjectId);
  }

  onNeedToFinish(subjectId : number){
    this.routerService.routeToFinishConcept(subjectId);
  }

  getDataForChart(){
      return  {datasets: [{
        label: "upcoming reviews per day",
        backgroundColor : 'rgba(0,0,0,0)',
        pointBackgroundColor : 'rgba(55, 160, 230)',
        data : this.data
      }],
    };
  }

  getOptionsForChart(){
    return {
      scales: {
          xAxes: [{
              type: 'time',
              time: {
                  unit: 'day',
                  displayFormats: {
                    day: 'MMM D'
                  },
                  round : 'day',
                  parser : 'MMM D',
                  tooltipFormat : 'MMM D'
              }
              
          }],
          yAxes :[{
            ticks: {
              min : 0,
              stepSize : 1
            }
          }]
      },
      elements : {
        line : {
          borderColor:'rgba(55, 160, 230, 0.6)',
          lineTension: 0,
          cubicInterpolationMode : 'monotone'
        }
      }
  };
  }


}
