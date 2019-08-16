import { Entity } from 'src/app/core/model/entity.model';

export class InvoiceRunMessage extends Entity {

  constructor(
    public message?: string,
    public id: number = null) {
    super(id);
  }

  static cloned(invoiceRunMessage: InvoiceRunMessage): InvoiceRunMessage {
    return new InvoiceRunMessage(
      invoiceRunMessage.message,
      invoiceRunMessage.id
    );
  }
}
