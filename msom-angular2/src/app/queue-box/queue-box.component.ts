import { Component, OnInit, Input } from '@angular/core';
import {Task } from '../shared/contracts'
import {tasksWaitingTimeAvg,processingTimeAvg } from '../shared/utils'
@Component({
  moduleId: module.id,
  selector: 'app-queue-box',
  template: `
              <div class="tasks-box">
                <p class="simulation-parameters">
                  <span> Tasks in queue: <em>{{tasks.length}}</em> </span> <br/>
                  <span> Queue Value: <em>{{getQueueValue()}}</em> </span> <br/>
                  <span> Avg. waiting time: <em>{{getAvgWaitingTime()}}</em> </span>
                </p>
                <p class="details-toogle"> - Show/hide details - </p>
                <ul class="tasks-list">
                  <li *ngFor="let task of tasks">ID {{task.id}} - Task1 [{{task.type}}] {{task.processed}}% {TWT: {{task.totalWaitingTime}}; TPT: {{task.totalProcessingTime}}; PC: {{task.processingCount}}}</li>
                </ul>
              </div>
              `
})
export class QueueBoxComponent implements OnInit {
@Input() tasks : Task[];
  constructor() {}

  ngOnInit() {
  }
  getQueueValue(){
     " ? what is queue value?? "
     return 60;
  }
  getAvgWaitingTime(){
      let value = tasksWaitingTimeAvg(this.tasks);
      return isNaN(+value) ? 0 : value;
  }
}
