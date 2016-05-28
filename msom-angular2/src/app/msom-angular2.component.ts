import { Component } from '@angular/core';
import {RouteSegment} from'@angular/Router'
import { Routes, Router, ROUTER_DIRECTIVES } from '@angular/router';
import { SimulateComponent} from './+simulate/simulate.component'

@Component({
  moduleId: module.id,
  selector: 'msom-angular2-app',
  templateUrl: 'msom-angular2.component.html',
  styleUrls: ['msom-angular2.component.css'],
  directives: [ROUTER_DIRECTIVES ]
})
@Routes([
  {path: '/simulate/:id', component: SimulateComponent},
  {path: '*',        component: SimulateComponent}
])

export class MsomAngular2AppComponent {
  constructor(private router: Router){

  }
  ngOnInit() {
  }
  title = 'msom-angular2 works!';
}
