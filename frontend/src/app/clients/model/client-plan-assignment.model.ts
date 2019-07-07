import { Entity } from 'src/app/core/model/entity.model';
import { Client } from './client.model';
import { Plan } from 'src/app/plans/model/plan.model';

export class ClientPlanAssignment extends Entity {

  public constructor(
    public validSince?: Date,
    public client?: Client,
    public plan?: Plan,
    public id: number = null) {
    super(id);
  }

  public static cloned(clientPlanAssignment: ClientPlanAssignment): ClientPlanAssignment {
    return new ClientPlanAssignment(
      clientPlanAssignment.validSince,
      clientPlanAssignment.client == null ? null : Client.cloned(clientPlanAssignment.client),
      clientPlanAssignment.plan == null ? null : Plan.cloned(clientPlanAssignment.plan),
      clientPlanAssignment.id
    );
  }
}
