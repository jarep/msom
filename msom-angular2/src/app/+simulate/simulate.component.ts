import { Component, OnInit } from '@angular/core';
import {SimulationService} from '../simulation.service'
import {TaskDispatcher} from '../shared/contracts'
import { Observable} from 'rxjs/Observable'
import { OnActivate, Router, RouteSegment } from '@angular/router';
@Component({
  moduleId: module.id,
  selector: 'app-simulate',
  templateUrl: 'simulate.component.html'
})
export class SimulateComponent implements OnInit, OnActivate {
  taskDispatchers : Observable<TaskDispatcher[]>;
  constructor(private simulationService : SimulationService,private router: Router ) {}

  ngOnInit() {

  }
 routerOnActivate(curr: RouteSegment): void {
    let id = curr.getParam('id');
    console.log(id);
    this.simulationService.getSimulationState(id).subscribe(console.log);
  }
}
