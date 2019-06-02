import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MaterialModule } from '../core/material.module';

import { DefaultPipe } from '../core/default.pipe';
import { ReadingsUploadPageComponent } from './components/readings-upload-page/readings-upload-page.component';
import { ReadingsUploadProgressComponent } from './components/readings-upload-page/readings-upload-progress/readings-upload-progress.component';
import { ReadingsUploadControlComponent } from './components/readings-upload-page/readings-upload-control/readings-upload-control.component';
import { ReadingsUploadsHistoryPageComponent } from './components/readings-uploads-history-page/readings-uploads-history-page.component';
import { ReadingsUploadsFilterComponent } from './components/readings-uploads-history-page/readings-uploads-filter/readings-uploads-filter.component';
import { ReadingsUploadsListComponent } from './components/readings-uploads-history-page/readings-uploads-list/readings-uploads-list.component';

@NgModule({
  declarations: [
    DefaultPipe,
    ReadingsUploadPageComponent,
    ReadingsUploadProgressComponent,
    ReadingsUploadControlComponent,
    ReadingsUploadsHistoryPageComponent,
    ReadingsUploadsListComponent,
    ReadingsUploadsFilterComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule
  ],
  providers: []
})
export class ReadingsModule { }
