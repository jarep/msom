import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
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
                <p class="details-toogle"  (click)="toggleShow()"> <a>- Show/hide details - </a></p>
                <div *ngIf="show" class="tasks-list">
                <div class="progress" *ngFor="let task of tasks">
                 <div class="progress-bar" [ngClass]="getStyle(task.processed)"  role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" [ngStyle]="{'width': task.processed +'%'}">
                  <span style="color : black">ID {{task.id}} - Task1 [{{task.type}}] {{task.processed}}% {TWT: {{task.totalWaitingTime}}; TPT: {{task.totalProcessingTime}}; PC: {{task.processingCount}}}</span>
               </div>
                </div>
              </div>
              `,
styles : [`
.progress {
  position: relative;
}

.progress span {
    position: absolute;
    display: block;
    width: 100%;
    color: black;
}`]
})
export class QueueBoxComponent implements OnInit {
@Input() tasks : Task[];
@Input() show : boolean;
@Output() toggle = new EventEmitter<any>();
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
  toggleShow(){
    this.toggle.emit({});
  }
    getStyle(processed : number){
      if (processed >= 0 && processed < 33)
      return 'progress-bar-danger'
      if (processed >= 33 && processed < 66)
      return 'progress-bar-warning'
      if (processed >= 66 && processed < 100)
      return 'progress-bar-info'
      return 'progress-bar-success'
  }
}
