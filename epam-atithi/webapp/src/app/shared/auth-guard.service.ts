import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { VisitorService } from '../visitor/services/visitor.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate{

  constructor(private visitorService : VisitorService , private router : Router) { }

  canActivate(route : ActivatedRouteSnapshot , state : RouterStateSnapshot){
    if(this.visitorService.isAuthenticated()){
      return true
    }
    else {
      this.router.navigate(['/login'])
    }
  }
}
