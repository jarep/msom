import { bootstrap } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { MsomAngular2AppComponent, environment,  } from './app/';
import { ROUTER_PROVIDERS } from '@angular/router';

if (environment.production) {
  enableProdMode();
}

bootstrap(MsomAngular2AppComponent, [ROUTER_PROVIDERS]);

