export class MetersSearchCriteria {

  constructor(public serialNumber?: string) { }

  reset() {
    this.serialNumber = '';
  }
}
