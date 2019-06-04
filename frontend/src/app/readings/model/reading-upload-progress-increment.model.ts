import { Observable } from 'rxjs';
import { ProcessingResult } from './processing-result.model';

export class ReadingUploadProgressIncrement {

  private constructor(
    readonly processingResult: ProcessingResult,
    readonly progress?: number,
    readonly errorMessage?: string,
    readonly measuredValues?: number,
    readonly expectedValues?: number
  ) { }

  public static waiting(): ReadingUploadProgressIncrement {
    return new ReadingUploadProgressIncrement(ProcessingResult.WAITING);
  }

  public static progress(progress: number): ReadingUploadProgressIncrement {
    return new ReadingUploadProgressIncrement(ProcessingResult.WAITING, progress);
  }

  public static error(errorMessage: string): ReadingUploadProgressIncrement {
    return new ReadingUploadProgressIncrement(ProcessingResult.ERROR, 100, errorMessage);
  }

  public static success(measuredValues: number, expectedValues: number): ReadingUploadProgressIncrement {
    return new ReadingUploadProgressIncrement(ProcessingResult.SUCCESS, 100, null, measuredValues, expectedValues);
  }
}
