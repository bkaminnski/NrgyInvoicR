import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { MaterialModule } from './material.module';

import { LoginPageComponent } from './components/login-page/login-page.component';
import { NavigationComponent } from './components/navigation/navigation.component';
import { NoRecordsFoundComponent } from './components/no-records-found/no-records-found.component';
import { NotificationComponent } from './components/notification/notification.component';

@NgModule({
  declarations: [
    LoginPageComponent,
    NavigationComponent,
    NoRecordsFoundComponent,
    NotificationComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MaterialModule
  ],
  exports: [
    LoginPageComponent,
    NavigationComponent,
    NoRecordsFoundComponent
  ]
})
export class CoreModule { }
