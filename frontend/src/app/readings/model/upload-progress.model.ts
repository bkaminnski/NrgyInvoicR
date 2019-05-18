import { Observable } from 'rxjs';
import { UploadProgressItem } from './upload-progress-item.model';

export class UploadProgress {
  readonly items: UploadProgressItem[];
  readonly numberOfUploadedFiles: Observable<number>;

  constructor(items: UploadProgressItem[], numberOfUploadedFiles: Observable<number>) {
    this.items = items;
    this.numberOfUploadedFiles = numberOfUploadedFiles;
  }
}
