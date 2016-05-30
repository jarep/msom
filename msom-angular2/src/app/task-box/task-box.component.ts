import { Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
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
    <p class="details-toogle" (click)="toggleShow()"> <a>- Show/hide details -</a> </p>
      <div *ngIf="show" class="tasks-list">
      <div class="progress" *ngFor="let task of tasks">
        <div class="progress-bar progress-bar-striped active" [ngClass]="getStyle(task.processed)"  role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" [ngStyle]="{'width': task.processed +'%'}">
          <span style="color : black">ID {{task.id}} - Task1 [{{task.type}}] {{task.processed}}% {TWT: {{task.totalWaitingTime}}; TPT: {{task.totalProcessingTime}}; PC: {{task.processingCount}}}</span>
        </div>
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
export class TaskBoxComponent implements OnInit {
@Input() tasks : Task[];
@Input() show : boolean;
@Output() toggle = new EventEmitter<any>();
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
