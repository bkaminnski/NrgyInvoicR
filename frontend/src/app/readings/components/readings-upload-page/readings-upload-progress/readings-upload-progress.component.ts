import { Component, OnInit, Output } from '@angular/core';
import { ReadingsUploadDataSource } from '../readings-upload.datasource';
import { ProcessingResult } from 'src/app/readings/model/processing-result.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-readings-upload-progress',
  templateUrl: './readings-upload-progress.component.html',
  styleUrls: ['./readings-upload-progress.component.scss']
})
export class ReadingsUploadProgressComponent implements OnInit {
  public displayedColumns: string[] = ['fileName', 'progress', 'processingResult', 'errorMessage', 'measured', 'expected'];
  public ProcessingResult = ProcessingResult;
  @Output() uploadInProgress: Observable<boolean>;

  constructor(public dataSource: ReadingsUploadDataSource) {
    this.uploadInProgress = dataSource.uploadInProgress;
  }

  ngOnInit() {
  }
}
