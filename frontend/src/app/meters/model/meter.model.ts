import { Entity } from 'src/app/core/model/entity.model';

export class Meter extends Entity {
  readonly serialNumber: string;
  readonly createdDate: Date;

  constructor(serialNumber: string, id: number = null) {
    super(id);
    this.serialNumber = serialNumber;
  }

  static cloned(meter: Meter): Meter {
    return new Meter(meter.serialNumber, meter.id);
  }
}
