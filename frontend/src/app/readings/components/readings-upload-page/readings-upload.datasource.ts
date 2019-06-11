import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ReadingsUploadService } from './readings-upload.service';
import { ReadingsUploadSummary } from 'src/app/readings/model/readings-upload-summary';
import { ReadingUploadProgress } from 'src/app/readings/model/reading-upload-progress.model';
import { Injectable } from '@angular/core';
import { NotificationService } from 'src/app/core/components/notification/notification.service';

@Injectable()
export class ReadingsUploadDataSource implements DataSource<ReadingUploadProgress> {
  private readingsSubject = new BehaviorSubject<ReadingUploadProgress[]>([]);
  private uploadInProgressSubject = new BehaviorSubject<boolean>(false);

  public readings = this.readingsSubject.asObservable();
  public uploadInProgress = this.uploadInProgressSubject.asObservable();
  public numberOfFilesToUpload: number;
  public numberOfSuccessfulUploads: number;
  public numberOfFailedUploads: number;

  constructor(private readingsUploadService: ReadingsUploadService, private notificationService: NotificationService) { }

  connect(collectionViewer: CollectionViewer): Observable<ReadingUploadProgress[]> {
    return this.readings;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.readingsSubject.complete();
    this.uploadInProgressSubject.complete();
  }

  uploadFiles(files: File[]) {
    if (files.length === 0) {
      return;
    }
    this.uploadInProgressSubject.next(true);
    this.numberOfFilesToUpload = files.length;
    this.numberOfSuccessfulUploads = 0;
    this.numberOfFailedUploads = 0;
    const summary: ReadingsUploadSummary = this.readingsUploadService.uploadFiles(files);
    summary.increment
      .pipe(finalize(() => this.handleFinalize()))
      .subscribe(increment => {
        this.numberOfSuccessfulUploads = increment.numberOfSuccessfulUploads;
        this.numberOfFailedUploads = increment.numberOfFailedUploads;
      });
    this.readingsSubject.next(summary.readings);
  }

  private handleFinalize(): void {
    this.notificationService.info('Files upload was finished. Successful uploads: ' + this.numberOfSuccessfulUploads + ', failures: ' + this.numberOfFailedUploads + '.');
    return this.uploadInProgressSubject.next(false);
  }
}
