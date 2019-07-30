import { Entity } from 'src/app/core/model/entity.model';

export class Meter extends Entity {

  constructor(
    public serialNumber: string,
    public clientNumber?: string,
    public createdDate?: Date,
    id?: number
  ) {
    super(id);
  }

  static cloned(meter: Meter): Meter {
    return new Meter(meter.serialNumber, meter.clientNumber, meter.createdDate, meter.id);
  }
}
