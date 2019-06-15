import { Entity } from 'src/app/core/model/entity.model';
import { ClientsSearchCriteria } from './clients-search-criteria.model';

export class Client extends Entity {

  public constructor(readonly number?: string,
    readonly firstName?: string,
    readonly middleName?: string,
    readonly lastName?: string,
    readonly addressLine1?: string,
    readonly addressLine2?: string,
    readonly postalCode?: string,
    readonly city?: string,
    readonly createdDate?: Date,
    readonly id: number = null) {
    super(id);
  }

  public static fromClientsSearchCriteria(criteria: ClientsSearchCriteria) {
    return new Client(
      null,
      criteria.firstName,
      null,
      criteria.lastName,
      criteria.address,
      null,
      criteria.city,
      criteria.postalCode,
      null
    );
  }

  public static cloned(client: Client): Client {
    return new Client(
      client.number,
      client.firstName,
      client.middleName,
      client.lastName,
      client.addressLine1,
      client.addressLine2,
      client.city,
      client.postalCode,
      client.createdDate,
      client.id
    );
  }
}
