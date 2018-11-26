import { NgModule } from '@angular/core';

import {
  MatButtonModule,
  MatIconModule,
  MatFormFieldModule,
  MatInputModule,
  MatDatepickerModule,
  MatToolbarModule,
  MatCardModule,
  MatTableModule
} from '@angular/material';

import {
  MatMomentDateModule
} from '@angular/material-moment-adapter';

@NgModule({
  imports: [
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatToolbarModule,
    MatTableModule,
    MatCardModule,
    MatMomentDateModule
  ],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatToolbarModule,
    MatTableModule,
    MatCardModule,
    MatMomentDateModule
  ]
})
export class MaterialModule { }
