import { Component, OnInit } from '@angular/core';
import { ReadingsUploadDataSource } from '../readings-upload.datasource';

@Component({
  selector: 'app-upload-progress',
  templateUrl: './upload-progress.component.html'
})
export class UploadProgressComponent implements OnInit {
  displayedColumns: string[] = ['fileName', 'percentage'];

  constructor(public dataSource: ReadingsUploadDataSource) {
  }

  ngOnInit() {
  }
}
