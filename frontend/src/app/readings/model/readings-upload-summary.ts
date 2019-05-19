import { Observable } from 'rxjs';
import { ReadingUploadProgress } from './reading-upload-progress.model';

export class ReadingsUploadSummary {
  readonly readings: ReadingUploadProgress[];
  readonly numberOfSuccessfulUploads: Observable<number>;
  readonly numberOfFailedUploads: Observable<number>;

  constructor(readings: ReadingUploadProgress[], numberOfSuccessfulUploads: Observable<number>, numberOfFailedUploads: Observable<number>) {
    this.readings = readings;
    this.numberOfSuccessfulUploads = numberOfSuccessfulUploads;
    this.numberOfFailedUploads = numberOfFailedUploads;
  }
}
