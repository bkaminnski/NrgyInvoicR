import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { NavigationComponent } from './components/navigation/navigation.component';
import { MaterialModule } from './material.module';

@NgModule({
  declarations: [
    NavigationComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule
  ],
  exports: [
    NavigationComponent
  ]
})
export class CoreModule { }
