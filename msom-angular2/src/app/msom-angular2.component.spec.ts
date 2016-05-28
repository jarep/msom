import {
  beforeEachProviders,
  describe,
  expect,
  it,
  inject
} from '@angular/core/testing';
import { MsomAngular2AppComponent } from '../app/msom-angular2.component';

beforeEachProviders(() => [MsomAngular2AppComponent]);

describe('App: MsomAngular2', () => {
  it('should create the app',
      inject([MsomAngular2AppComponent], (app: MsomAngular2AppComponent) => {
    expect(app).toBeTruthy();
  }));

  it('should have as title \'msom-angular2 works!\'',
      inject([MsomAngular2AppComponent], (app: MsomAngular2AppComponent) => {
    expect(app.title).toEqual('msom-angular2 works!');
  }));
});
