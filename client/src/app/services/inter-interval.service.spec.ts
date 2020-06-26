import { TestBed } from '@angular/core/testing';

import { InterIntervalService } from './inter-interval.service';

describe('InterIntervalService', () => {
  let service: InterIntervalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InterIntervalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
