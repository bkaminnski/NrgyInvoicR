import { Component, OnInit, Output } from '@angular/core';
import { ReadingsUploadDataSource } from '../readings-upload.datasource';
import { ProcessingResult } from 'src/app/readings/model/processing-result.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-upload-progress',
  templateUrl: './upload-progress.component.html',
  styleUrls: ['./upload-progress.component.scss']
})
export class UploadProgressComponent implements OnInit {
  public displayedColumns: string[] = ['fileName', 'progress', 'processingResult', 'errorMessage'];
  public ProcessingResult = ProcessingResult;
  @Output() uploadInProgress: Observable<boolean>;

  constructor(public dataSource: ReadingsUploadDataSource) {
    this.uploadInProgress = dataSource.uploadInProgress;
  }

  ngOnInit() {
  }
}
