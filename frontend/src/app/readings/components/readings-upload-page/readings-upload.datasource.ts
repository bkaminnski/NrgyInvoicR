import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ReadingsUploadService } from './readings-upload.service';
import { ReadingsUploadSummary } from 'src/app/readings/model/readings-upload-summary';
import { ReadingUploadProgress } from 'src/app/readings/model/reading-upload-progress.model';
import { Injectable } from '@angular/core';

@Injectable()
export class ReadingsUploadDataSource implements DataSource<ReadingUploadProgress> {
  private readingsSubject = new BehaviorSubject<ReadingUploadProgress[]>([]);

  public readings = this.readingsSubject.asObservable();
  public loading = false;
  public numberOfFilesToUpload: number;
  public numberOfSuccessfulUploads: number;
  public numberOfFailedUploads: number;

  constructor(private readingsUploadService: ReadingsUploadService) { }

  connect(collectionViewer: CollectionViewer): Observable<ReadingUploadProgress[]> {
    return this.readings;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.readingsSubject.complete();
  }

  uploadFiles(files: File[]) {
    if (files.length === 0) {
      return;
    }
    this.loading = true;
    this.numberOfFilesToUpload = files.length;
    this.numberOfSuccessfulUploads = 0;
    this.numberOfFailedUploads = 0;
    const summary: ReadingsUploadSummary = this.readingsUploadService.uploadFiles(files);
    summary.numberOfSuccessfulUploads
      .pipe(finalize(() => this.loading = false))
      .subscribe(numberOfSuccessfulUploads => this.numberOfSuccessfulUploads = numberOfSuccessfulUploads);
    summary.numberOfFailedUploads
      .pipe(finalize(() => this.loading = false))
      .subscribe(numberOfFailedUploads => this.numberOfFailedUploads = numberOfFailedUploads);
    this.readingsSubject.next(summary.readings);
  }
}
