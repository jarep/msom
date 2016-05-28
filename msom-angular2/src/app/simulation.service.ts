import { Injectable } from '@angular/core';
import {Http, Response} from '@angular/http'
import {Observable} from "rxjs/Observable";
import {TaskDispatcher} from './shared/contracts'
@Injectable()
export class SimulationService {

  constructor(private http : Http) {
    
  }
  getSimulationState(id : string) : Observable<TaskDispatcher[]>{
          return this.http.get(
              `http://localhost:8080/msom/processingsystem/simulate?id=${id}`
          ).map(
              (res:Response) => {
                  return res.json()
              }
          )
      }
}
