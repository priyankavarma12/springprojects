import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomePageComponent } from './home-page/home-page.component';
import { MaterialModule } from '../material/material.module';
import { AdminService } from './service/admin.service';
import { InvitationComponent } from './invitation/invitation.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [HomePageComponent, InvitationComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  providers: [ AdminService ],
  exports: [HomePageComponent]
})
export class AdminModule { }
