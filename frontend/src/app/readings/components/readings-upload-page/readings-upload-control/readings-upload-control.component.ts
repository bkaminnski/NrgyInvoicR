import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { ReadingsUploadDataSource } from '../readings-upload.datasource';

@Component({
  selector: 'app-readings-upload-control',
  templateUrl: './readings-upload-control.component.html',
  styleUrls: ['./readings-upload-control.component.scss']
})
export class ReadingsUploadControlComponent implements OnInit {
  public uploadStarted = false;
  @ViewChild('fileInput', { static: true }) fileInput: ElementRef;

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
    this.fileInput.nativeElement.value = null;
  }
}
