import { Observable } from 'rxjs';
import { ProcessingResult } from './processing-result.model';

export class ReadingUploadProgress {
  readonly fileName: string;
  readonly progress: Observable<number>;
  readonly processingResult: Observable<ProcessingResult>;

  constructor(fileName: string, progress: Observable<number>, processingResult: Observable<ProcessingResult>) {
    this.fileName = fileName;
    this.progress = progress;
    this.processingResult = processingResult;
  }
}
