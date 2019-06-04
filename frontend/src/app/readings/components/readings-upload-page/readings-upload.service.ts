import { HttpClient, HttpRequest, HttpEventType, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subject, of, BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { ReadingUpload } from '../../model/reading-upload.model';
import { ReadingUploadProgress } from '../../model/reading-upload-progress.model';
import { ReadingUploadProgressIncrement } from '../../model/reading-upload-progress-increment.model';
import { ReadingsUploadSummary } from '../../model/readings-upload-summary';
import { ReadingsUploadSummaryIncrement } from '../../model/readings-upload-summary-increment.model';

@Injectable()
export class ReadingsUploadService {
  private readings: ReadingUploadProgress[] = [];
  private numberOfFiles: number;
  private summaryIncrement: ReadingsUploadSummaryIncrement;
  private summaryIncrementSubject: Subject<ReadingsUploadSummaryIncrement>;

  constructor(private http: HttpClient) { }

  public uploadFiles(files: File[]): ReadingsUploadSummary {
    this.readings = [];
    this.numberOfFiles = files.length;
    this.summaryIncrement = ReadingsUploadSummaryIncrement.starting();
    this.summaryIncrementSubject = new BehaviorSubject<ReadingsUploadSummaryIncrement>(this.summaryIncrement);
    files.forEach(file => this.uploadFile(file));
    return new ReadingsUploadSummary(this.readings, this.summaryIncrementSubject.asObservable());
  }

  private uploadFile(file: File) {
    const increment = new BehaviorSubject<ReadingUploadProgressIncrement>(ReadingUploadProgressIncrement.waiting());
    this.readings.push(new ReadingUploadProgress(file.name, increment.asObservable()));
    this.http
      .request(this.prepareHttpRequest(file))
      .pipe(
        catchError((errorResponse) => this.handleError(errorResponse, increment))
      )
      .subscribe(event => this.handleEvent(event, increment));
  }

  private handleError(errorResponse: HttpErrorResponse, increment: Subject<ReadingUploadProgressIncrement>) {
    this.summaryIncrement = this.summaryIncrement.incrementNumberOfFailedUploads();
    this.summaryIncrementSubject.next(this.summaryIncrement);
    increment.next(ReadingUploadProgressIncrement.error(errorResponse.error.errorMessage));
    increment.complete();
    this.tryCompletingSummaryIncrementSubject();
    return of([]);
  }

  private prepareHttpRequest(file: File): HttpRequest<FormData> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return new HttpRequest('POST', '/api/readingsUploads', formData, { reportProgress: true });
  }

  private handleEvent(event: any, increment: Subject<ReadingUploadProgressIncrement>) {
    if (event.type === HttpEventType.UploadProgress) {
      increment.next(ReadingUploadProgressIncrement.progress(Math.round(100 * event.loaded / event.total)));
    } else if (event instanceof HttpResponse) {
      const readingUpload: ReadingUpload = event.body;
      const readingSpread = readingUpload.reading.readingSpread;
      increment.next(ReadingUploadProgressIncrement.success(readingSpread.numberOfMeasuredValues, readingSpread.numberOfExpectedValues));
      increment.complete();
      this.summaryIncrement = this.summaryIncrement.incrementNumberOfSuccessfulUploads();
      this.summaryIncrementSubject.next(this.summaryIncrement);
      this.tryCompletingSummaryIncrementSubject();
    }
  }

  private tryCompletingSummaryIncrementSubject() {
    if (this.summaryIncrement.addsUpTo(this.numberOfFiles)) {
      this.summaryIncrementSubject.complete();
    }
  }
}
