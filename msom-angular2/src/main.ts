import { bootstrap } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { MsomAngular2AppComponent, environment  } from './app/';
import { ROUTER_PROVIDERS } from '@angular/router';
import { SimulationService } from './app/simulation.service'
import {HTTP_PROVIDERS } from '@angular/http'
import "rxjs/Rx";

if (environment.production) {
  enableProdMode();
}

bootstrap(MsomAngular2AppComponent, [ROUTER_PROVIDERS, SimulationService, HTTP_PROVIDERS]);

