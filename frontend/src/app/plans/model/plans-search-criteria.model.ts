export class PlansSearchCriteria {

  constructor(public name?: string) { }

  reset() {
    this.name = '';
  }
}
