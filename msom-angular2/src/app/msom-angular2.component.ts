import { Component } from '@angular/core';
import {RouteSegment} from'@angular/Router'
import { Routes, Router, ROUTER_DIRECTIVES } from '@angular/router';
import { SimulateComponent} from './+simulate/simulate.component'

@Component({
  moduleId: module.id,
  selector: 'msom-angular2-app',
  template : `
        <router-outlet></router-outlet>
      <a [routerLink]="['/simulate',12]"></a>`,
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
}
