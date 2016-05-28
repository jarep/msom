import {
  beforeEachProviders,
  it,
  describe,
  expect,
  inject
} from '@angular/core/testing';
import { SimulationService } from './simulation.service';

describe('Simulation Service', () => {
  beforeEachProviders(() => [SimulationService]);

  it('should ...',
      inject([SimulationService], (service: SimulationService) => {
    expect(service).toBeTruthy();
  }));
});
