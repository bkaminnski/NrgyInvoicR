import { HttpClient, HttpRequest, HttpEventType, HttpResponse } from '@angular/common/http';
import { Subject } from 'rxjs';
import { ReadingsUploadSummary } from '../../model/readings-upload-summary';
import { ReadingUploadProgress } from '../../model/reading-upload-progress.model';
import { Injectable } from '@angular/core';

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
    const uploadProgress = new Subject<number>();
    this.readings.push(new ReadingUploadProgress(file.name, uploadProgress.asObservable()));
    this.http
      .request(this.prepareHttpRequest(file))
      .subscribe(event => this.handleEvent(event, uploadProgress));
  }

  private prepareHttpRequest(file: File): HttpRequest<FormData> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return new HttpRequest('POST', '/api/readings', formData, { reportProgress: true });
  }

  private handleEvent(event: any, uploadProgress: Subject<number>) {
    if (event.type === HttpEventType.UploadProgress) {
      uploadProgress.next(event.loaded / event.total);
    } else if (event instanceof HttpResponse) {
      uploadProgress.complete();
      this.numberOfSuccessfulUploadsSubject.next(++this.numberOfSuccessfulUploads);
      if (this.numberOfSuccessfulUploads + this.numberOfFailedUploads === this.numberOfFiles) {
        this.numberOfSuccessfulUploadsSubject.complete();
        this.numberOfFailedUploadsSubject.complete();
      }
    }
  }
}
