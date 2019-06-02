import { Reading } from './reading.model';

export class ReadingUpload {
  date: Date;
  fileName: string;
  status: string;
  errorMessage: string;
  reading: Reading;
}
