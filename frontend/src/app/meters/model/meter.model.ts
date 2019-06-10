export class Meter {
  id: number;
  serialNumber: string;
  createdDate: Date;

  constructor(serialNumber: string) {
    this.serialNumber = serialNumber;
  }
}
