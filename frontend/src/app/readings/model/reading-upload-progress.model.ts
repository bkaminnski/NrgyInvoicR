import { Observable } from 'rxjs';
import { ProcessingResult } from './processing-result.model';

export class ReadingUploadProgress {
  readonly fileName: string;
  readonly progress: Observable<number>;
  readonly processingResult: Observable<ProcessingResult>;
  readonly errorMessage: Observable<string>;

  constructor(fileName: string, progress: Observable<number>, processingResult: Observable<ProcessingResult>, errorMessage: Observable<string>) {
    this.fileName = fileName;
    this.progress = progress;
    this.processingResult = processingResult;
    this.errorMessage = errorMessage;
  }
}
