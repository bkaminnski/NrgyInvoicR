import { Entity } from './entity.model';

export class Page<T extends Entity> {
  content: T[] = [];
  totalElements = 0;

  static cloned<T extends Entity>(page: Page<T>): Page<T> {
    const cloned = new Page<T>();
    cloned.content = page.content;
    cloned.totalElements = page.totalElements;
    return cloned;
  }

  indexOf(entity: T): number {
    return this.content.findIndex(e => e.id === entity.id);
  }
}
