import { DataSource, CollectionViewer } from '@angular/cdk/collections';
import { BehaviorSubject, Observable } from 'rxjs';
import { ReadingsUploadService } from '../readings-upload/readings-upload.service';
import { UploadProgress } from 'src/app/readings/model/upload-progress.model';
import { UploadProgressItem } from 'src/app/readings/model/upload-progress-item.model';
import { finalize } from 'rxjs/operators';

export class UploadProgressDataSource implements DataSource<UploadProgressItem> {
  private itemsSubject = new BehaviorSubject<UploadProgressItem[]>([]);

  public items = this.itemsSubject.asObservable();
  public loading = false;
  public numberOfUploadedFiles: number;

  constructor(private readingsUploadService: ReadingsUploadService) { }

  connect(collectionViewer: CollectionViewer): Observable<UploadProgressItem[]> {
    return this.items;
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.itemsSubject.complete();
  }

  uploadFiles(files: File[]) {
    this.loading = true;
    this.numberOfUploadedFiles = 0;
    const uploadProgress: UploadProgress = this.readingsUploadService.uploadFiles(files);
    this.itemsSubject.next(uploadProgress.items);
    uploadProgress.numberOfUploadedFiles
      .pipe(finalize(() => this.loading = false))
      .subscribe(numberOfUploadedFiles => this.numberOfUploadedFiles = numberOfUploadedFiles);
  }
}
