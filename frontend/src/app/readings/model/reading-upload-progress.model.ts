import { Observable } from 'rxjs';

export class ReadingUploadProgress {
  readonly fileName: string;
  readonly percentage: Observable<number>;

  constructor(fileName: string, percentage: Observable<number>) {
    this.fileName = fileName;
    this.percentage = percentage;
  }
}
