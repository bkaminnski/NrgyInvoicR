import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEventType, HttpResponse } from '@angular/common/http';
import { Subject } from 'rxjs';

import { UploadProgress } from 'src/app/readings/model/upload-progress.model';
import { UploadProgressItem } from 'src/app/readings/model/upload-progress-item.model';

@Injectable()
export class ReadingsUploadService {

  constructor(private http: HttpClient) { }

  public uploadFiles(files: File[]): UploadProgress {
    const items: UploadProgressItem[] = [];
    let uploaded = 0;
    const numberOfUploadedFiles = new Subject<number>();

    files.forEach(file => {
      const formData: FormData = new FormData();
      formData.append('file', file, file.name);
      const httpRequest = new HttpRequest('POST', '/api/readings', formData, { reportProgress: true });

      const fileProgress = new Subject<number>();

      this.http.request(httpRequest).subscribe(event => {
        if (event.type === HttpEventType.UploadProgress) {
          fileProgress.next(event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          fileProgress.complete();
          numberOfUploadedFiles.next(++uploaded);
          if (uploaded === files.length) {
            numberOfUploadedFiles.complete();
          }
        }
        items.push(new UploadProgressItem(file.name, fileProgress.asObservable()));
      });
    });

    return new UploadProgress(items, numberOfUploadedFiles.asObservable());
  }
}
