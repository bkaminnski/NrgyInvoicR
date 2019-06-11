import { Reading } from './reading.model';
import { Entity } from 'src/app/core/model/entity.model';

export class ReadingUpload extends Entity {
  date: Date;
  fileName: string;
  status: string;
  errorMessage: string;
  reading: Reading;
}
