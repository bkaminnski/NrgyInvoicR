import { Entity } from 'src/app/core/model/entity.model';
import { FixedFees } from './fixed-fees.model';

export class PlanVersion extends Entity {
  public validSince: Date;
  public fixedFees: FixedFees;
  public expression: string;
  public description: string;
}
