import { async, ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import {FormsModule} from '@angular/forms';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { AddConceptComponent } from './add-concept.component';
import { Concept } from '../models/concept';
import { DebugElement } from '@angular/core';

/**
 * There seems to be a bug with click events in Angular. 
 * https://github.com/angular/angular/issues/17104
 */

describe('AddConceptComponent', () => {
  let component: AddConceptComponent;
  let fixture: ComponentFixture<AddConceptComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddConceptComponent ],
      imports: [FormsModule, HttpClientTestingModule]
    })
    .compileComponents();
  }));

  
  beforeEach(fakeAsync(() => {
    fixture = TestBed.createComponent(AddConceptComponent);
    component = fixture.componentInstance;
    fixture.detectChanges(); //Trigger a change detection cycle for AppConceptComponent.
    tick(); //Causes Angular to run change detection for the entire application.
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Inputting a value in the input named #nameInput should also bind a value to the model concept.name', () => {
    let nameInput : HTMLInputElement  = fixture.nativeElement.querySelector("#nameInput");
    expect(nameInput.value).toBe("");
    expect(component.concept.name).toBe("");
   
    nameInput.value = "REST";
    nameInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();    
    expect(nameInput.value).toBe("REST");
     
    fixture.whenStable().then(()=>{
      expect(nameInput.value).toBe(component.concept.name);
    });
  });

  it('Inputting a value in the input named #explanationInput should also bind a value to the model concept.explanation', () => {
    let explanationTextArea : HTMLInputElement  = fixture.nativeElement.querySelector("#explanationTextArea");
    expect(explanationTextArea.value).toBe("");
    expect(component.concept.explanation).toBe("");

    explanationTextArea.value = "REST - Stands for Representation State Transfer";
    explanationTextArea.dispatchEvent(new Event('input'));
    fixture.detectChanges();    
    expect(explanationTextArea.value).toBe("REST - Stands for Representation State Transfer");
  
    fixture.whenStable().then(()=>{
      expect(component.concept.explanation).toBe("REST - Stands for Representation State Transfer");
    });
  });

  it('Checking the "I reviewed what I didnt know" should set concept.reviewed to true', ()=>{
    let reviewedCheckbox : HTMLInputElement = fixture.nativeElement.querySelector("#reviewedCheckBox");
    expect(reviewedCheckbox.checked).toBe(false);
    expect(component.concept.simplified).toBe(false);
    //Tick the reviewed checkbox.
    reviewedCheckbox.checked = true;
    reviewedCheckbox.dispatchEvent(new Event('change'));
    fixture.detectChanges();
    
    expect(reviewedCheckbox.checked).toBe(true); 

    fixture.whenStable().then(()=>{
      expect(component.concept.reviewed).toBe(true);
    })
  });

  it('Checking the "I simplified the language used" should set concept.simplified to be true', ()=>{
    let simplifiedCheckbox : HTMLInputElement = fixture.nativeElement.querySelector("#simplifiedCheckBox");
    expect(simplifiedCheckbox.checked).toBe(false);
    expect(component.concept.simplified).toBe(false);

    //Tick the simplified language checkbox
    simplifiedCheckbox.checked = true;
    simplifiedCheckbox.dispatchEvent(new Event('change'));
    fixture.detectChanges(); 
    expect(simplifiedCheckbox.checked).toBe(true);
    
    fixture.whenStable().then(()=>{
      expect(component.concept.simplified).toBe(true);
    })
  });

  it('Having the name input populated should enable the save button', ()=>{
    let saveButton : HTMLButtonElement = fixture.nativeElement.querySelector("#saveButton");
    let nameInput : HTMLInputElement = fixture.nativeElement.querySelector("#nameInput");
    console.log(saveButton);
    console.log(saveButton.disabled);
   
    /**
     * We need to tell the TestBed to perform databinding. Not sure why this is needed, since it's called
     * in beforeEach, but without calling it again. The test won't pass, this is because the disabled
     * property on the saveButton which is set by Angular (Data binding), is never done or is done late.
     */

    fixture.detectChanges();
    expect(saveButton.disabled).toBe(true);
    
    //Add text to nameInput, this should enable the button.
    nameInput.value = "REST";
    nameInput.dispatchEvent(new Event("input")); //Only input event's work here. I honestly don;t know.

    //Let Angular know, you've changed the input value.
    fixture.detectChanges();
    expect(nameInput.value).toBe("REST");
    expect(saveButton.disabled).toBe(false);
  })


it('Having the name input populated should enable the save button', ()=>{
  let doneButton : HTMLButtonElement = fixture.nativeElement.querySelector("#doneButton");
  let nameInput : HTMLInputElement = fixture.nativeElement.querySelector("#nameInput");
  let explanationTextArea : HTMLInputElement = fixture.nativeElement.querySelector("#explanationTextArea");
  let reviewedCheckBox : HTMLInputElement = fixture.nativeElement.querySelector("#reviewedCheckBox");
  let simplifiedCheckbox : HTMLInputElement = fixture.nativeElement.querySelector("#simplifiedCheckBox");

  console.log(doneButton);
  console.log(doneButton.disabled);
   
  fixture.detectChanges();
  expect(doneButton.disabled).toBe(true);

  nameInput.value = "REST";
  explanationTextArea.value = "Representational State Transfer";
  reviewedCheckBox.checked = true;
  simplifiedCheckbox.checked = true;

  nameInput.dispatchEvent(new Event('input'));
  explanationTextArea.dispatchEvent(new Event('input'));
  reviewedCheckBox.dispatchEvent(new Event('change'));
  simplifiedCheckbox.dispatchEvent(new Event('change'));
  fixture.detectChanges();

  expect(nameInput.value).toBe("REST");
  expect(explanationTextArea.value).toBe("Representational State Transfer");
  expect(reviewedCheckBox.checked).toBe(true);
  expect(simplifiedCheckbox.checked).toBe(true);
  expect(doneButton.disabled).toBe(false);

});


it('SimplifiedCheckBox is required for enabling the done button', ()=>{
  let doneButton : HTMLButtonElement = fixture.nativeElement.querySelector("#doneButton");
  let nameInput : HTMLInputElement = fixture.nativeElement.querySelector("#nameInput");
  let explanationTextArea : HTMLInputElement = fixture.nativeElement.querySelector("#explanationTextArea");
  let reviewedCheckBox : HTMLInputElement = fixture.nativeElement.querySelector("#reviewedCheckBox");
  let simplifiedCheckbox : HTMLInputElement = fixture.nativeElement.querySelector("#simplifiedCheckBox");

  console.log(doneButton);
  console.log(doneButton.disabled);
   
  fixture.detectChanges();
  expect(doneButton.disabled).toBe(true);

  nameInput.value = "REST";
  explanationTextArea.value = "Representational State Transfer";
  reviewedCheckBox.checked = true;
  //Do not check the simplified checkbox.
  simplifiedCheckbox.checked = false;

  nameInput.dispatchEvent(new Event('input'));
  explanationTextArea.dispatchEvent(new Event('input'));
  reviewedCheckBox.dispatchEvent(new Event('change'));
  simplifiedCheckbox.dispatchEvent(new Event('change'));
  fixture.detectChanges();

  expect(nameInput.value).toBe("REST");
  expect(explanationTextArea.value).toBe("Representational State Transfer");
  expect(reviewedCheckBox.checked).toBe(true);
  expect(simplifiedCheckbox.checked).toBe(false);
  //Expect saveButton to still be disabled.
  expect(doneButton.disabled).toBe(true);
});

it('ReviewedCheckBox is required for enabling the done button.', ()=>{
  let doneButton : HTMLButtonElement = fixture.nativeElement.querySelector("#doneButton");
  let nameInput : HTMLInputElement = fixture.nativeElement.querySelector("#nameInput");
  let explanationTextArea : HTMLInputElement = fixture.nativeElement.querySelector("#explanationTextArea");
  let reviewedCheckBox : HTMLInputElement = fixture.nativeElement.querySelector("#reviewedCheckBox");
  let simplifiedCheckbox : HTMLInputElement = fixture.nativeElement.querySelector("#simplifiedCheckBox");

  console.log(doneButton);
  console.log(doneButton.disabled);
   
  fixture.detectChanges();
  expect(doneButton.disabled).toBe(true);

  nameInput.value = "REST";
  explanationTextArea.value = "Representational State Transfer";
  //Do not check the reviewed checkbox.
  reviewedCheckBox.checked = false;
  simplifiedCheckbox.checked = true;

  nameInput.dispatchEvent(new Event('input'));
  explanationTextArea.dispatchEvent(new Event('input'));
  reviewedCheckBox.dispatchEvent(new Event('change'));
  simplifiedCheckbox.dispatchEvent(new Event('change'));
  fixture.detectChanges();

  expect(nameInput.value).toBe("REST");
  expect(explanationTextArea.value).toBe("Representational State Transfer");
  expect(reviewedCheckBox.checked).toBe(false);
  expect(simplifiedCheckbox.checked).toBe(true);
  //Expect saveButton to still be disabled.
  expect(doneButton.disabled).toBe(true);
});

});
