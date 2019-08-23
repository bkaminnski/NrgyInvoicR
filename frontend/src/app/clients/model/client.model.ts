import { Entity } from 'src/app/core/model/entity.model';
import { ClientsSearchCriteria } from './clients-search-criteria.model';
import { Meter } from 'src/app/meters/model/meter.model';

export class Client extends Entity {

  public constructor(
    public number?: string,
    public firstName?: string,
    public middleName?: string,
    public lastName?: string,
    public addressLine1?: string,
    public addressLine2?: string,
    public city?: string,
    public postalCode?: string,
    public createdDate?: Date,
    public meter?: Meter,
    public id: number = null) {
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
      null,
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
      client.meter,
      client.id
    );
  }
}
