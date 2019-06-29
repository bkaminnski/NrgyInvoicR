import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MaterialModule } from '../core/material.module';

import { PlansPageComponent } from './components/plans-page/plans-page.component';
import { MarketingNamesListComponent } from './components/plans-page/marketing-names-list/marketing-names-list.component';
import { CoreModule } from '../core/core.module';

@NgModule({
  declarations: [
    PlansPageComponent,
    MarketingNamesListComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    CoreModule
  ]
})
export class PlansModule { }
