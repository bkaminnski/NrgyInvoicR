import { Entity } from 'src/app/core/model/entity.model';

export class Plan extends Entity {
  readonly name: string;
  readonly description: string;
}
