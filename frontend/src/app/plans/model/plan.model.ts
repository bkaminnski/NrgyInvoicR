import { Entity } from 'src/app/core/model/entity.model';

export class Plan extends Entity {

  public constructor(
    public name?: string,
    public description?: string,
    public id: number = null
  ) {
    super(id);
  }

  public static cloned(plan: Plan): Plan {
    return new Plan(
      plan.name,
      plan.description,
      plan.id
    );
  }
}
