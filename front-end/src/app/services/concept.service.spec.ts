import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { ConceptService } from './concept.service';

describe('ConceptService', () => {
  beforeEach(() => TestBed.configureTestingModule({
    imports :[
      HttpClientTestingModule
    ]
  }));

  it('should be created', () => {
    const service: ConceptService = TestBed.get(ConceptService);
    expect(service).toBeTruthy();
  });
});
