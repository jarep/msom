import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http'
import {Observable} from "rxjs/Observable";
import {TaskDispatcher, Task, ProcessingSystem} from './shared/contracts'
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
          return Observable.interval(1000).map(x => 
              getProcessingSystem()
          )
      }
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
        name : "some name",
        processingUnits : [
            {
                id : "567",
                name: "MOD 01",
                cores : 3,
                efficiency : 7,
                availableTypes : ["A","B","C"],
                tasks: [getTask(), getTask(),getTask()]
            }
            
        ]
    }
}

var getTask : () => Task = () => {
    return {
        id: (Math.round(Math.random() * 100)).toString(),
        type: "A",
        processed : +(Math.random() * 100).toFixed(2),
        totalWaitingTime : +(Math.random() * 100).toFixed(2),
        totalProcessingTime :+(Math.random() * 100).toFixed(2),
        processingCount : 2,
        inProgress: true
    }
}