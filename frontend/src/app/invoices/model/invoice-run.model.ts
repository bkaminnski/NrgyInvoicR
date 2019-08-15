import { Entity } from 'src/app/core/model/entity.model';

export class InvoiceRun extends Entity {

  constructor(
    public sinceClosed?: Date,
    public untilOpen?: Date,
    public issueDate?: Date,
    public firstInvoiceNumber?: number,
    public numberTemplate?: string,
    public status?: string,
    public id: number = null) {
    super(id);
  }

  static cloned(invoiceRun: InvoiceRun): InvoiceRun {
    return new InvoiceRun(
      invoiceRun.sinceClosed,
      invoiceRun.untilOpen,
      invoiceRun.issueDate,
      invoiceRun.firstInvoiceNumber,
      invoiceRun.numberTemplate,
      invoiceRun.status,
      invoiceRun.id
    );
  }
}