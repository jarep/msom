import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http'
import {Observable} from "rxjs/Observable";
import {TaskDispatcher, Task, ProcessingSystem, ProcessingUnit} from './shared/contracts'
@Injectable()
export class SimulationService {

  constructor(private http : Http) {
    
  }
  getSimulationState(id : string) : Observable<ProcessingSystem>{
          //return this.http.get(
          //    `http://localhost:8080/msom/processingsystem/simulate/${id}`
          //    ).map(
          //    (res:Response) => {
          //        return res.json()
          //    }
          //)
          var processingSystem = getProcessingSystem();
          return Observable.interval(500).map(() => 
               inc(processingSystem)
          )
      }
}
var inc : (processingSystem : ProcessingSystem) => ProcessingSystem = (processingSystem) => {
     processingSystem.taskDispatchers.forEach(x => x.processingUnits.forEach(p => p.tasks.forEach(t => { 
        var inProgress = Math.random() > 0.5 ? true : false;
        t.id =  t.id,
        t.inProgress = inProgress,
        t.type= t.type,
        t.processed = inProgress ? +(t.processed + Math.random()*2).toFixed(2) : t.processed,
        t.totalWaitingTime = +(t.totalWaitingTime+ Math.random()).toFixed(2),
        t.totalProcessingTime = +(t.totalProcessingTime+ Math.random()).toFixed(2),
        t.processingCount = +Math.round(Math.random() * 10)
    })))
    return processingSystem;
}

var getProcessingSystem : () => ProcessingSystem = () => {
    return {
        id : "12",
        name : "Some Processing System",
        taskDispatchers : [getTaskDispatcher() , getTaskDispatcher(), getTaskDispatcher()]
    }
}

var getTaskDispatcher : () => TaskDispatcher = () => {
    return {
        id : "123",
        name : "Controller 1",
        processingUnits : [
            {
                id : "567",
                name: "MOD 01",
                cores : 3,
                efficiency : 7,
                availableTypes : ["A","B","C"],
                tasks: [getTask(), getTask(),getTask(),getTask(),getTask(),getTask()]
            }
            
        ]
    }
}

var getTask : () => Task = () => {
    return {
        id: "10",
        type: Math.random() > 0.66 ? "A" : Math.random() > 0.5 ? "B" : "C" ,
        processed : +(Math.random().toFixed(2)),
        totalWaitingTime : +(Math.random()).toFixed(2),
        totalProcessingTime :+(Math.random()).toFixed(2),
        processingCount : +Math.round(Math.random() * 10),
        inProgress: Math.random() > 0.5 ? true : false
    }
}