import { HttpClient, HttpRequest, HttpEventType, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subject, of, BehaviorSubject } from 'rxjs';
import { ReadingsUploadSummary } from '../../model/readings-upload-summary';
import { ReadingUploadProgress } from '../../model/reading-upload-progress.model';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { ReadingUploadProgressIncrement } from '../../model/reading-upload-progress-increment.model';
import { ReadingUpload } from '../../model/reading-upload.model';

@Injectable()
export class ReadingsUploadService {
  private readings: ReadingUploadProgress[] = [];
  private numberOfFiles: number;
  private numberOfSuccessfulUploads: number;
  private numberOfSuccessfulUploadsSubject: Subject<number>;
  private numberOfFailedUploads: number;
  private numberOfFailedUploadsSubject: Subject<number>;

  constructor(private http: HttpClient) { }

  public uploadFiles(files: File[]): ReadingsUploadSummary {
    this.readings = [];
    this.numberOfFiles = files.length;
    this.numberOfSuccessfulUploads = 0;
    this.numberOfSuccessfulUploadsSubject = new Subject<number>();
    this.numberOfFailedUploads = 0;
    this.numberOfFailedUploadsSubject = new Subject<number>();
    files.forEach(file => this.uploadFile(file));
    return new ReadingsUploadSummary(this.readings, this.numberOfSuccessfulUploadsSubject.asObservable(), this.numberOfFailedUploadsSubject.asObservable());
  }

  private uploadFile(file: File) {
    const increments = new BehaviorSubject<ReadingUploadProgressIncrement>(ReadingUploadProgressIncrement.waiting());
    this.readings.push(new ReadingUploadProgress(file.name, increments.asObservable()));
    this.http
      .request(this.prepareHttpRequest(file))
      .pipe(
        catchError((errorResponse) => this.handleError(errorResponse, increments))
      )
      .subscribe(event => this.handleEvent(event, increments));
  }

  private handleError(errorResponse: HttpErrorResponse, increments: Subject<ReadingUploadProgressIncrement>) {
    this.numberOfFailedUploadsSubject.next(++this.numberOfFailedUploads);
    increments.next(ReadingUploadProgressIncrement.error(errorResponse.error.errorMessage));
    increments.complete();
    this.tryCompletingSubjects();
    return of([]);
  }

  private prepareHttpRequest(file: File): HttpRequest<FormData> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return new HttpRequest('POST', '/api/readingsUploads', formData, { reportProgress: true });
  }

  private handleEvent(event: any, increments: Subject<ReadingUploadProgressIncrement>) {
    if (event.type === HttpEventType.UploadProgress) {
      increments.next(ReadingUploadProgressIncrement.progress(Math.round(100 * event.loaded / event.total)));
    } else if (event instanceof HttpResponse) {
      const readingUpload: ReadingUpload = event.body;
      const readingSpread = readingUpload.reading.readingSpread;
      increments.next(ReadingUploadProgressIncrement.success(readingSpread.numberOfMeasuredValues, readingSpread.numberOfExpectedValues));
      increments.complete();
      this.numberOfSuccessfulUploadsSubject.next(++this.numberOfSuccessfulUploads);
      this.tryCompletingSubjects();
    }
  }

  private tryCompletingSubjects() {
    if (this.numberOfSuccessfulUploads + this.numberOfFailedUploads === this.numberOfFiles) {
      this.numberOfSuccessfulUploadsSubject.complete();
      this.numberOfFailedUploadsSubject.complete();
    }
  }
}
