export class Meter {
  readonly id: number;
  readonly serialNumber: string;
  readonly createdDate: Date;

  constructor(serialNumber: string, id: number = null) {
    this.serialNumber = serialNumber;
    this.id = id;
  }

  static cloned(meter: Meter): Meter {
    return new Meter(meter.serialNumber, meter.id);
  }
}
