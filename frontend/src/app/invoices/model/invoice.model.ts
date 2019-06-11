import { Entity } from 'src/app/core/model/entity.model';

export class Invoice extends Entity {
  readonly number: string;
  readonly issueDate: Date;
}
