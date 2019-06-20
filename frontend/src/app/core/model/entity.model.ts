export class Entity {

  constructor(readonly id: number) { }

  public isNew(): boolean {
    return this.id == null;
  }
}
