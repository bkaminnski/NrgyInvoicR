import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../core/material.module';

import { DefaultPipe } from '../core/default.pipe';
import { ReadingsUploadPageComponent } from './components/readings-upload-page/readings-upload-page.component';
import { UploadProgressComponent } from './components/readings-upload-page/upload-progress/upload-progress.component';
import { UploadControlComponent } from './components/readings-upload-page/upload-control/upload-control.component';

@NgModule({
  declarations: [
    DefaultPipe,
    ReadingsUploadPageComponent,
    UploadProgressComponent,
    UploadControlComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  providers: [
  ]
})
export class ReadingsModule { }
