import { Entity } from 'src/app/core/model/entity.model';
import { FixedFees } from './fixed-fees.model';
import { Plan } from './plan.model';

export class PlanVersion extends Entity {

  public constructor(
    public validSince?: Date,
    public fixedFees: FixedFees = new FixedFees(),
    public expression: string = '.01.01-12.31\n..1-7\n...0-23:0.00000',
    public description?: string,
    public plan?: Plan,
    public id: number = null) {
    super(id);
  }

  public static cloned(planVersion: PlanVersion): PlanVersion {
    return new PlanVersion(
      planVersion.validSince,
      planVersion.fixedFees === null ? null : FixedFees.cloned(planVersion.fixedFees),
      planVersion.expression,
      planVersion.description,
      null,
      planVersion.id
    );
  }
}
