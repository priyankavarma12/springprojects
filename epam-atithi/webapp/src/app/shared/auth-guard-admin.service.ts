import { Injectable } from '@angular/core';
import { AdminService } from '../admin/service/admin.service';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardAdminService implements CanActivate{

  constructor(private adminService : AdminService , private router : Router)  { }

  canActivate(route : ActivatedRouteSnapshot , state : RouterStateSnapshot){
    if(this.adminService.isAuthenticated()){
      return true
    }
    else{
      this.router.navigate(['/login'])
    }
  }
}
