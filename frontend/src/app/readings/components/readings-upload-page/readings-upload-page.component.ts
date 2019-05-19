import { Component, OnInit } from '@angular/core';
import { ReadingsUploadDataSource } from './readings-upload.datasource';
import { ReadingsUploadService } from './readings-upload.service';

@Component({
  templateUrl: './readings-upload-page.component.html',
  providers: [
    ReadingsUploadService,
    ReadingsUploadDataSource
  ]
})
export class ReadingsUploadPageComponent implements OnInit {
  constructor() {
  }

  ngOnInit() {
  }
}
