import { Component, OnInit } from '@angular/core';
import { ReadingsUploadDataSource } from './readings-upload.datasource';
import { ReadingsUploadService } from './readings-upload.service';
import { DeactivableComponent } from 'src/app/core/can-deactivate.guard';
import { Observable } from 'rxjs';
import { DialogService } from 'src/app/core/dialog.service';

@Component({
  templateUrl: './readings-upload-page.component.html',
  providers: [
    ReadingsUploadService,
    ReadingsUploadDataSource
  ]
})
export class ReadingsUploadPageComponent implements OnInit, DeactivableComponent {
  private uploadInProgres: boolean;

  constructor(private dialogService: DialogService) {
  }

  ngOnInit() {
  }

  canDeactivate(): Observable<boolean> | Promise<boolean> | boolean {
    if (this.uploadInProgres) {
      return this.dialogService.confirm('Upload is in progress. Are you sure to leave the page?');
    }
    return true;
  }

  updateProgressStatus(uploadInProgress: boolean) {
    this.uploadInProgres = uploadInProgress;
  }
}
