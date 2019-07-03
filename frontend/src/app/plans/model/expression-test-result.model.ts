import { LineError } from './line-error.model';
import { FlattenedBucket } from './flattened-bucket.model';

export class ExpressionTestResult {
  public valid: boolean;
  public coversWholeYear: boolean;
  public lineError: LineError;
  public flattenedBuckets: FlattenedBucket[];
}
