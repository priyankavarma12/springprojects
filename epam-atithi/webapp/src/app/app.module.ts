import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AdminModule } from './admin/admin.module';
import { MaterialModule } from './material/material.module';
import { VisitorDetailsComponent } from './components/visitor-registration/visitor-details.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { VisitorModule } from './visitor/visitor.module';
import { HttpClientModule } from '@angular/common/http';
import { VisitEventsComponent } from './components/visit-events/visit-events.component';
import { LoginComponent } from './components/login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    VisitorDetailsComponent,
    VisitEventsComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    HttpClientModule,
    AdminModule,
    VisitorModule,
   ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
