import { Component, OnInit } from '@angular/core';
import {SimulationService} from '../simulation.service'
import {ProcessingSystem, Task, ProcessingUnit} from '../shared/contracts'
import { Observable} from 'rxjs/Observable'
import { OnActivate, Router, RouteSegment } from '@angular/router'
import {TaskBoxComponent} from '../task-box/task-box.component'
import {QueueBoxComponent} from '../queue-box/queue-box.component'
import {TaskStatsComponent} from '../task-stats/task-stats.component'

@Component({
  moduleId: module.id,
  directives: [TaskBoxComponent, QueueBoxComponent, TaskStatsComponent], 
  selector: 'app-simulate',
  templateUrl: 'simulate.component.html'
})
export class SimulateComponent implements OnInit, OnActivate {
  private id;
  processingSystem : ProcessingSystem;
  showProcessingUnit = [];
  constructor(private simulationService : SimulationService,private router: Router ) {}

  ngOnInit() {
      
  }
 routerOnActivate(curr: RouteSegment): void {
    this.id = curr.getParam('id');
    this.simulationService.getSimulationState(this.id).subscribe(x => {
      this.processingSystem = x;
    });
  }
  getTasksInProgress(tasks : Task[]){
    return tasks.filter(x => x.inProgress);
  }
  getTasksInQueue(tasks : Task[]){
     return tasks.filter(x => !x.inProgress);
  }
  getTasksByType(){
    var processingUnits = Array<ProcessingUnit>().concat([],...this.processingSystem.taskDispatchers.map(td => td.processingUnits));
    var tasks = Array<Task>().concat([],...processingUnits.map(x => x.tasks));
    return _.values(_.groupBy(_.sortBy(tasks,x => x.type) , (t) => t.type));
  }
  show(processingUnit : ProcessingUnit, type : string){
     return this.showProcessingUnit.find(x => x.id == processingUnit.id && type== x.type ) ? false : true;
  }
  toggle(processingUnit: ProcessingUnit, type : string){
    if (!this.show(processingUnit, type)) {
      _.remove(this.showProcessingUnit, (x) => x.id == processingUnit.id && x.type == type) 
    } else { 
      this.showProcessingUnit.push( {id : processingUnit.id, type: type} )
    }
  }
  startSimulation(){
     this.simulationService.startSimulation(this.id).subscribe(x => {
      this.processingSystem = x;
    });
  }
}
