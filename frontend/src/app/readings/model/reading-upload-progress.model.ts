import { Observable } from 'rxjs';
import { ReadingUploadProgressIncrement } from './reading-upload-progress-increment.model';

export class ReadingUploadProgress {

  constructor(readonly fileName: string, readonly increment: Observable<ReadingUploadProgressIncrement>) { }
}
