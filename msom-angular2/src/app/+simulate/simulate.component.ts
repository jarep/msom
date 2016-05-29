import { Component, OnInit } from '@angular/core';
import {SimulationService} from '../simulation.service'
import {ProcessingSystem, Task, ProcessingUnit} from '../shared/contracts'
import { Observable} from 'rxjs/Observable'
import { OnActivate, Router, RouteSegment } from '@angular/router';
import {TaskBoxComponent} from '../task-box/task-box.component'
import {QueueBoxComponent} from '../queue-box/queue-box.component'

@Component({
  moduleId: module.id,
  directives: [TaskBoxComponent, QueueBoxComponent], 
  selector: 'app-simulate',
  templateUrl: 'simulate.component.html'
})
export class SimulateComponent implements OnInit, OnActivate {
  processingSystem : ProcessingSystem;
  constructor(private simulationService : SimulationService,private router: Router ) {}

  ngOnInit() {
      
  }
 routerOnActivate(curr: RouteSegment): void {
    let id = curr.getParam('id');
    this.simulationService.getSimulationState(id).subscribe(x => {
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
    console.log(tasks.reduce((acc, task) => acc[task.type].push(task), []));
    return tasks.reduce((acc, task) => {
      if (!acc[task.type]) acc[task.type] = []
      acc[task.type].push(task)
      return acc;
    }, []);
  }
}
