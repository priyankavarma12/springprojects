import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VisitorComponent } from './visitor/visitor.component';
import { MaterialModule } from '../material/material.module';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { QrCodeComponent } from './qr-code/qr-code.component';
  
@NgModule({
  declarations: [VisitorComponent, EditProfileComponent, QrCodeComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  exports : [VisitorComponent , EditProfileComponent]
})
export class VisitorModule { }
