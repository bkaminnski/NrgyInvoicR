import { Component, OnInit } from '@angular/core';
import { ReadingsUploadDataSource } from '../readings-upload.datasource';
import { ProcessingResult } from 'src/app/readings/model/processing-result.model';

@Component({
  selector: 'app-upload-progress',
  templateUrl: './upload-progress.component.html',
  styleUrls: ['./upload-progress.component.scss']
})
export class UploadProgressComponent implements OnInit {
  public displayedColumns: string[] = ['fileName', 'progress', 'processingResult'];
  public ProcessingResult = ProcessingResult;

  constructor(public dataSource: ReadingsUploadDataSource) {
  }

  ngOnInit() {
  }
}
