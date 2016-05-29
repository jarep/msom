import { Component, OnInit, Input } from '@angular/core';
import {Task } from '../shared/contracts'
import {tasksProcessedAvg,processingTimeAvg } from '../shared/utils'
@Component({
  moduleId: module.id,
  selector: 'app-task-box',
  template: `
  <div class="tasks-box">
    <p class="simulation-parameters">
      <span> Tasks in progress <em>{{tasks.length}}</em> </span> <br/>
      <span> Processed  <em>{{getTasksInProgressAvg()}} %</em> </span> <br/>
      <span> Avg. processing time: <em>{{getTasksProcessingTimeAvg()}}</em> </span>
    </p>
    <p class="details-toogle"> - Show/hide details - </p>
      <ul class="tasks-list">
        <li *ngFor="let task of tasks">ID {{task.id}} - Task1 [{{task.type}}] {{task.processed}}% {TWT: {{task.totalWaitingTime}}; TPT: {{task.totalProcessingTime}}; PC: {{task.processingCount}}}</li>
      </ul>
  </div>
              `
})
export class TaskBoxComponent implements OnInit {
@Input() tasks : Task[];
  constructor() {}

  ngOnInit() {
  }
  getTasksInProgressAvg(){
          let value = tasksProcessedAvg(this.tasks);
      return isNaN(+value) ? 0 : value;
  }
  getTasksProcessingTimeAvg(){
              let value = processingTimeAvg(this.tasks);
      return isNaN(+value) ? 0 : value;
  }
}
