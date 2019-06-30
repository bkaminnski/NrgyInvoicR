import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../core/material.module';

import { PlansPageComponent } from './components/plans-page/plans-page.component';
import { PlansListComponent } from './components/plans-page/plans-list/plans-list.component';
import { CoreModule } from '../core/core.module';

@NgModule({
  declarations: [
    PlansPageComponent,
    PlansListComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    CoreModule
  ]
})
export class PlansModule { }
