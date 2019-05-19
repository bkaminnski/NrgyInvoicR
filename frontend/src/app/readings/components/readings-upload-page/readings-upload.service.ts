import { HttpClient, HttpRequest, HttpEventType, HttpResponse } from '@angular/common/http';
import { Subject, of, BehaviorSubject } from 'rxjs';
import { ReadingsUploadSummary } from '../../model/readings-upload-summary';
import { ReadingUploadProgress } from '../../model/reading-upload-progress.model';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { ProcessingResult } from '../../model/processing-result.model';

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
    const progress = new Subject<number>();
    const processingResult = new BehaviorSubject<ProcessingResult>(ProcessingResult.WAITING);
    this.readings.push(new ReadingUploadProgress(file.name, progress.asObservable(), processingResult.asObservable()));
    this.http
      .request(this.prepareHttpRequest(file))
      .pipe(
        catchError(() => this.handleError(processingResult))
      )
      .subscribe(event => this.handleEvent(event, progress, processingResult));
  }

  private handleError(processingResult: Subject<ProcessingResult>) {
    this.numberOfFailedUploadsSubject.next(++this.numberOfFailedUploads);
    processingResult.next(ProcessingResult.ERROR);
    processingResult.complete();
    this.tryCompletingSubjects();
    return of([]);
  }

  private prepareHttpRequest(file: File): HttpRequest<FormData> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return new HttpRequest('POST', '/api/readings', formData, { reportProgress: true });
  }

  private handleEvent(event: any, progress: Subject<number>, processingResult: Subject<ProcessingResult>) {
    if (event.type === HttpEventType.UploadProgress) {
      progress.next(event.loaded / event.total);
    } else if (event instanceof HttpResponse) {
      processingResult.next(ProcessingResult.SUCCESS);
      processingResult.complete();
      progress.complete();
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
