import { NgModule } from '@angular/core';

import {
  MatButtonModule,
  MatIconModule,
  MatFormFieldModule,
  MatInputModule,
  MatDatepickerModule,
  MatToolbarModule,
  MatCardModule,
  MatTableModule,
  MatPaginatorModule,
  MatSortModule,
  MatProgressSpinnerModule,
  MatProgressBarModule,
  MatMenuModule,
  MatCheckboxModule,
  MatDialogModule,
  MatSnackBarModule
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
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatMomentDateModule,
    MatProgressBarModule,
    MatMenuModule,
    MatCheckboxModule,
    MatDialogModule,
    MatSnackBarModule
  ],
  exports: [
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatToolbarModule,
    MatCardModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatProgressSpinnerModule,
    MatMomentDateModule,
    MatProgressBarModule,
    MatMenuModule,
    MatCheckboxModule,
    MatDialogModule,
    MatSnackBarModule
  ]
})
export class MaterialModule { }
