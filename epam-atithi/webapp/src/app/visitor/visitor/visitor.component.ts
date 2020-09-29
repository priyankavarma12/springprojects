import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { VisitorService } from '../services/visitor.service';
import { Visitor } from 'src/app/models/visitor.model';

@Component({
  selector: 'app-visitor',
  templateUrl: './visitor.component.html',
  styleUrls: ['./visitor.component.scss']
})
export class VisitorComponent implements OnInit {
  visitorData : Visitor ;
  upcomingVisits : any;
  pastVisits : any;
  pendingActions : any; 
  upcomingDataSource : MatTableDataSource<Visitor[]>
  pastDataSource : MatTableDataSource<Visitor[]>
  pendingDataSource : MatTableDataSource<Visitor[]>
  constructor(private router : Router , private visitorService : VisitorService) { }

  upcomingDisplayedColumns : string [] = ['visitDate', 'duration', 'purpose', 'poc' , 'status' , 'visitorResponse' , 'qrCode'];
  pastDisplayedColumns: string[] = ['visitDate', 'duration', 'purpose', 'poc' , 'visitorResponse'];
  actions : string[] = ['visitDate','duration', 'purpose', 'poc' , 'action']
 

  ngOnInit(): void {
    this.visitorService.getVisitorDetails().subscribe((data : Visitor) => {
      this.visitorData = data
      this.upcomingVisits = data.invitations.filter((visitorInvitations : any) => {
        return (!visitorInvitations.visitCompleted && visitorInvitations.visitorResponse === 'Y')
      })
      this.pastVisits = data.invitations.filter((visitorInvitations : any) => {
        return (visitorInvitations.visitCompleted || visitorInvitations.visitorResponse === 'N')
      })
      this.pendingActions = data.invitations.filter((visitorInvitations : any) => {
        return visitorInvitations.visitorResponse === 'P'
      })
      this.upcomingDataSource = new MatTableDataSource<Visitor[]>(this.upcomingVisits)
      this.pastDataSource = new MatTableDataSource<Visitor[]>(this.pastVisits)
      this.pendingDataSource = new MatTableDataSource<Visitor[]>(this.pendingActions)
      })
  }

  navigateToEditProfile(){
    this.router.navigate(['./edit-profile'])
  }

  accept(invitation , index){
    this.visitorService.updateInvitationByVisitor(invitation , true).subscribe(data => {
    window.location.reload()
    })
  }

  reject(invitation , index){
    this.visitorService.updateInvitationByVisitor(invitation , false).subscribe(data => {
    window.location.reload()
    })
  }

  qrCode(invitation){
   window.open('qrCode/' + invitation.invitationId)
  }
}
