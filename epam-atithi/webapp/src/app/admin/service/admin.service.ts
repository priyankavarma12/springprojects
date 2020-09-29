import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Visitor } from 'src/app/models/visitor.model';
import { map, shareReplay } from 'rxjs/operators';
import { Invitation } from 'src/app/models/invitation.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  adminBaseUrl: string = environment.baseUrl + '/admin/';

  constructor(private _http: HttpClient) { }

  getVisitorsByAdminId(adminId: string): Observable<Visitor> {
    const url = this.adminBaseUrl + adminId + '/visitors';
    return this._http.get<Visitor[]>(url).pipe(
      map( (visitors: any) => {
        return visitors;
      } ),
      shareReplay(1)
    );
  }

  createVisitor(adminId: number, visitor: Visitor): Observable<any> {
    const url = this.adminBaseUrl + adminId + '/visitors';
    return this._http.post<Visitor>(url, visitor);
  }

  createInvitation(adminId: number, invitation: Invitation): Observable<any> {
    const url = this.adminBaseUrl + adminId + '/invitations';
    return this._http.post<Invitation>(url, invitation);
  }

  authenticateAdmin(adminDetails){
    return this._http.post(this.adminBaseUrl + 'authenticate' , {"email" : adminDetails.email , "password" : adminDetails.password})
  }

  isAuthenticated(){
    let adminId = localStorage.getItem('adminId')
    return adminId!==null
  }
  
}
