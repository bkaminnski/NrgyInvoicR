import { Entity } from 'src/app/core/model/entity.model';
import { InvoiceRun } from './invoice-run.model';
import { Client } from 'src/app/clients/model/client.model';
import { PlanVersion } from 'src/app/plans/model/plan-version.model';
import { InvoiceLine } from './invoice-line.model';

export class Invoice extends Entity {
  readonly number: string;
  readonly issueDate: Date;
  readonly grossTotal: number;
  readonly invoiceRun: InvoiceRun;
  readonly client: Client;
  readonly planVersion: PlanVersion;
  readonly invoiceLines: InvoiceLine[];
}
