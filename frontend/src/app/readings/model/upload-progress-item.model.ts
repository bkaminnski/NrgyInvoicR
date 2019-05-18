import { Observable } from 'rxjs';

export class UploadProgressItem {
  readonly fileName: string;
  readonly percentage: Observable<number>;

  constructor(fileName: string, percentage: Observable<number>) {
    this.fileName = fileName;
    this.percentage = percentage;
  }
}
