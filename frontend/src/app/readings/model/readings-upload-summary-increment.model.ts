export class ReadingsUploadSummaryIncrement {

  private constructor(readonly numberOfSuccessfulUploads: number, readonly numberOfFailedUploads: number) { }

  public static starting(): ReadingsUploadSummaryIncrement {
    return new ReadingsUploadSummaryIncrement(0, 0);
  }

  public incrementNumberOfSuccessfulUploads(): ReadingsUploadSummaryIncrement {
    return new ReadingsUploadSummaryIncrement(this.numberOfSuccessfulUploads + 1, this.numberOfFailedUploads);
  }

  public incrementNumberOfFailedUploads(): ReadingsUploadSummaryIncrement {
    return new ReadingsUploadSummaryIncrement(this.numberOfSuccessfulUploads, this.numberOfFailedUploads + 1);
  }

  public addsUpTo(numberOfFiles: number): boolean {
    return this.numberOfSuccessfulUploads + this.numberOfFailedUploads === numberOfFiles;
  }
}
