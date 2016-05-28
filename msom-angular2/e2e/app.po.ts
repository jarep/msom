export class MsomAngular2Page {
  navigateTo() {
    return browser.get('/');
  }

  getParagraphText() {
    return element(by.css('msom-angular2-app h1')).getText();
  }
}
