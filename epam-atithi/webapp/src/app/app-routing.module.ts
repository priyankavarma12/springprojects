import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { VisitorDetailsComponent } from './components/visitor-registration/visitor-details.component';
import { HomePageComponent } from './admin/home-page/home-page.component';
import { VisitorComponent } from './visitor/visitor/visitor.component';
import { EditProfileComponent } from './visitor/edit-profile/edit-profile.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuardService } from './shared/auth-guard.service';
import { AuthGuardAdminService } from './shared/auth-guard-admin.service';
import { QrCodeComponent } from './visitor/qr-code/qr-code.component';


const routes: Routes = [
  { path : 'login' , component : LoginComponent},
  { path: 'admin', component: HomePageComponent , canActivate : [AuthGuardAdminService]},
  { path: 'visitor-registration', component: VisitorDetailsComponent , canActivate : [AuthGuardAdminService]},
  { path: 'visitor-details', component: VisitorDetailsComponent , canActivate : [AuthGuardAdminService] },
  { path : 'visitor' , component : VisitorComponent , canActivate : [AuthGuardService]},
  { path : 'edit-profile' , component : EditProfileComponent , canActivate : [AuthGuardService]},
  { path : 'qrCode/:invitationId' , component : QrCodeComponent},
  { path: '',  redirectTo: '/login', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
