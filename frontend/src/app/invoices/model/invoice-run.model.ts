import { Entity } from 'src/app/core/model/entity.model';
import { InvoiceRunProgress } from './invoice-run-progress.model';
import { InvoiceRunMessage } from './invoice-run-message.model';

export class InvoiceRun extends Entity {
  public messages: InvoiceRunMessage[];

  constructor(
    public sinceClosed?: Date,
    public untilOpen?: Date,
    public issueDate?: Date,
    public firstInvoiceNumber?: number,
    public numberTemplate?: string,
    public status?: string,
    public progress?: InvoiceRunProgress,
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
      invoiceRun.progress === null ? null : InvoiceRunProgress.cloned(invoiceRun.progress),
      invoiceRun.id
    );
  }
}
