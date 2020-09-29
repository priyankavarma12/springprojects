import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Visitor } from 'src/app/models/visitor.model';
import { map, shareReplay } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class VisitorService {
  visitorDetails : any;
  baseUrl: string = environment.baseUrl;
 
  constructor(private _http: HttpClient) { }

  getVisitorById(visitorId: number): Observable<Visitor> {
    const url = this.baseUrl + '/visitors/'+ visitorId;
    return this._http.get<Visitor>(url).pipe(
      map( (visitor: Visitor) => {
        return visitor;
      } ),
      shareReplay(1)
    );
  }

  getVisitorDetails(){
    let visitorId = localStorage.getItem('visitorId')
    return this._http.get(this.baseUrl + 'visitors/' + visitorId)
  }

  updateVisitorDetails(visitorDetails : Visitor){
    return this._http.put(this.baseUrl + 'visitors/' + visitorDetails.visitorId , visitorDetails)
  }

  authenticateVisitor(visitorCredentials){
    this.visitorDetails = visitorCredentials
    return this._http.post(this.baseUrl + 'visitors/authenticate' ,{"email" : visitorCredentials.email , "password" : visitorCredentials.password})
  }

  updateInvitationByVisitor(invitation , response){
    return this._http.put(this.baseUrl + 'visitors/' + invitation.visitorId + '/invitations/' + invitation.invitationId + '/visitorResponse' , {"visitorResponse" : response})
  }

  isAuthenticated(){
    let visitorId = localStorage.getItem('visitorId')
    return visitorId !== null
  }

  getQrCode(invitationId){
    return this._http.get(this.baseUrl + 'visitors/invitations/' + invitationId + '/qrCode' , { responseType: 'blob' })
  }
}
