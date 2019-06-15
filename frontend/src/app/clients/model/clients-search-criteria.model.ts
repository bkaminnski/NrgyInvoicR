export class ClientsSearchCriteria {
  public number: string;
  public firstName: string;
  public lastName: string;
  public address: string;
  public postalCode: string;
  public city: string;

  reset() {
    this.number = '';
    this.firstName = '';
    this.lastName = '';
    this.address = '';
    this.postalCode = '';
    this.city = '';
  }
}
