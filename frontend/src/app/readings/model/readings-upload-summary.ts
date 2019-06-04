import { Observable } from 'rxjs';
import { ReadingUploadProgress } from './reading-upload-progress.model';
import { ReadingsUploadSummaryIncrement } from './readings-upload-summary-increment.model';

export class ReadingsUploadSummary {

  constructor(readonly readings: ReadingUploadProgress[], readonly increment: Observable<ReadingsUploadSummaryIncrement>) { }
}
