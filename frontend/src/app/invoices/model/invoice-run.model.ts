import { Entity } from 'src/app/core/model/entity.model';

export class InvoiceRun extends Entity {

  constructor(
    public sinceClosed?: Date,
    public untilOpen?: Date,
    public issueDate?: Date,
    public id: number = null) {
    super(id);
  }

  static cloned(invoiceRun: InvoiceRun): InvoiceRun {
    return new InvoiceRun(invoiceRun.sinceClosed, invoiceRun.untilOpen, invoiceRun.issueDate, invoiceRun.id);
  }
}
