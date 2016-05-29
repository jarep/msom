import { Component, OnInit, Input } from '@angular/core';
import {Task} from '../shared/contracts'
import {processingTimeAvg, tasksWaitingTimeAvg} from '../shared/utils'
@Component({
  moduleId: module.id,
  selector: 'app-task-stats',
  template: `          <div>
            <h4>Type {{getType()}}</h4>
            <p>Generated tasks: <strong>{{tasks.length}}</strong></p>
            <p>Finished tasks: <strong>{{getFinishedTasksCount()}}</strong></p>
            <p>Avg. waiting time: <strong>{{getAvgWaitingTime()}}</strong></p>
            <p>Avg. processing time: <strong>{{getTasksProcessingTimeAvg()}}</strong></p>
          </div>
              `
})
export class TaskStatsComponent implements OnInit {
  @Input() tasks: Task[];
  constructor() { }

  ngOnInit() {
  }
  getFinishedTasksCount() {
    return this.tasks.filter(x => x.processed == 100).length;
  }
  getAvgWaitingTime() {
    let value = tasksWaitingTimeAvg(this.tasks);
    return isNaN(+value) ? 0 : value;
  }
  getTasksProcessingTimeAvg() {
    let value = processingTimeAvg(this.tasks);
    return isNaN(+value) ? 0 : value;
  }
  getType(){
    if(this.tasks) return this.tasks[0].type;
  }
}
