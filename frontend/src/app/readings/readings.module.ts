import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../core/material.module';

import { ReadingsUploadService } from './components/readings-import-page/readings-upload/readings-upload.service';
import { ReadingsImportPageComponent } from './components/readings-import-page/readings-import-page.component';
import { ReadingsUploadComponent } from './components/readings-import-page/readings-upload/readings-upload.component';
import { UploadProgressComponent } from './components/readings-import-page/upload-progress/upload-progress.component';
import { DefaultPipe } from '../core/default.pipe';

@NgModule({
  declarations: [
    ReadingsImportPageComponent,
    ReadingsUploadComponent,
    UploadProgressComponent,
    DefaultPipe
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  providers: [
    ReadingsUploadService
  ]
})
export class ReadingsModule { }
