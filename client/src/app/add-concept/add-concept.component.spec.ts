import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import {FormsModule} from '@angular/forms';

import { AddConceptComponent } from './add-concept.component';
import { asNativeElements } from '@angular/core';

describe('AddConceptComponent', () => {
  let component: AddConceptComponent;
  let fixture: ComponentFixture<AddConceptComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddConceptComponent ],
      imports: [FormsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddConceptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Inputting a value in the input named #name should also bind a value to the  model concept.name', () => {
   
    let nameInput : HTMLInputElement  = fixture.nativeElement.querySelector("#name");
    nameInput.value = "REST";
    nameInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();  
      
    expect(nameInput.value).toBe("REST");
    
    fixture.whenStable().then(()=>{
      expect(nameInput.value).toBe(component.concept.name);
    });
  });
});
