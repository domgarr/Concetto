import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewConceptComponent } from './view-concept.component';

describe('ViewConceptComponent', () => {
  let component: ViewConceptComponent;
  let fixture: ComponentFixture<ViewConceptComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewConceptComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewConceptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
