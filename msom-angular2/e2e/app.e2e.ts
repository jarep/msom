import { MsomAngular2Page } from './app.po';

describe('msom-angular2 App', function() {
  let page: MsomAngular2Page;

  beforeEach(() => {
    page = new MsomAngular2Page();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('msom-angular2 works!');
  });
});
