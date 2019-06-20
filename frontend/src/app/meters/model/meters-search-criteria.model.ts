export class MetersSearchCriteria {

  constructor(public serialNumber?: string, public onlyUnassigned?: boolean) { }

  reset() {
    this.serialNumber = '';
    this.onlyUnassigned = false;
  }
}
