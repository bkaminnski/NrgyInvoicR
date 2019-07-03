import { Entity } from 'src/app/core/model/entity.model';
import { FixedFees } from './fixed-fees.model';

export class PlanVersion extends Entity {

  public constructor(
    public validSince?: Date,
    public fixedFees?: FixedFees,
    public expression?: string,
    public description?: string,
    public id: number = null) {
    super(id);
  }

  public static cloned(planVersion: PlanVersion): PlanVersion {
    return new PlanVersion(
      planVersion.validSince,
      planVersion.fixedFees,
      planVersion.expression,
      planVersion.description,
      planVersion.id
    );
  }
}
