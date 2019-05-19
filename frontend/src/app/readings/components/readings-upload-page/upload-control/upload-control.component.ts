import { Component, OnInit } from '@angular/core';
import { ReadingsUploadDataSource } from '../readings-upload.datasource';

@Component({
  selector: 'app-upload-control',
  templateUrl: './upload-control.component.html',
  styleUrls: ['./upload-control.component.scss']
})
export class UploadControlComponent implements OnInit {
  public uploadStarted = false;

  constructor(public dataSource: ReadingsUploadDataSource) { }

  ngOnInit() { }

  uploadFiles($event: any) {
    const files: File[] = [];
    for (const key in $event.target.files) {
      if ($event.target.files.hasOwnProperty(key)) {
        files.push($event.target.files[key]);
      }
    }
    this.dataSource.uploadFiles(files);
    this.uploadStarted = true;
  }
}
