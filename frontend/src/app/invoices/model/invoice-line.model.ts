import { Entity } from 'src/app/core/model/entity.model';

export class InvoiceLine extends Entity {
  readonly description: string;
  readonly unitPrice: number;
  readonly quantity: number;
  readonly unit: string;
  readonly netTotal: number;
  readonly vat: number;
  readonly grossTotal: number;
}
