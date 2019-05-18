import { Component, OnInit } from '@angular/core';
import { UploadProgressDataSource } from './upload-progress.datasource';
import { ReadingsUploadService } from '../readings-upload/readings-upload.service';

@Component({
  selector: 'app-upload-progress',
  templateUrl: './upload-progress.component.html',
  styleUrls: ['./upload-progress.component.scss']
})
export class UploadProgressComponent implements OnInit {
  dataSource: UploadProgressDataSource;
  displayedColumns: string[] = ['fileName', 'percentage'];

  constructor(private readingsUploadService: ReadingsUploadService) {
    this.dataSource = new UploadProgressDataSource(readingsUploadService);
  }

  ngOnInit() {
  }

  uploadFiles($event: any) {
    const files: File[] = [];
    for (const key in $event.target.files) {
      if ($event.target.files.hasOwnProperty(key)) {
        files.push($event.target.files[key]);
      }
    }
    this.dataSource.uploadFiles(files);
  }
}
