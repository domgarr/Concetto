import { Component, OnInit, Output, EventEmitter  } from '@angular/core';
import { Subject } from '../models/subject';
import { SubjectService } from '../services/subject.service';
import { Router } from '@angular/router';
import { UserRoutingModule } from '../user/user-routing.module';

@Component({
  selector: 'app-add-subject',
  templateUrl: './add-subject.component.html',
  styleUrls: ['./add-subject.component.css']
})
export class AddSubjectComponent implements OnInit {

  constructor(private subjectService : SubjectService, private router : Router) { }

  subject : Subject;
  @Output() subjectSaved = new EventEmitter<Subject>();

  ngOnInit() {
    this.subject = new Subject();
  }

  onSaveSubject(){
    this.subjectService.saveSubject(this.subject).subscribe( subject =>{
      this.subjectSaved.emit(subject); 
      this.subject = new Subject();
    });
  }
}
