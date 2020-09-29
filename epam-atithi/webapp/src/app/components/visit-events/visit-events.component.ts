import { Component, OnInit, Input } from '@angular/core';
import { Invitation } from 'src/app/models/invitation.model';
import { MatTableDataSource } from '@angular/material/table';
import { CONSTANTS } from '../../shared/constants';

@Component({
  selector: 'app-visit-events',
  templateUrl: './visit-events.component.html',
  styleUrls: ['./visit-events.component.scss']
})
export class VisitEventsComponent implements OnInit {

  @Input()
  set INVITATIONS(value: Invitation[]) {
    if(value && !this.invitations){
      this.invitations = value;
      this.setInvitationTableDataSource(this.invitations);
    }
  }

  constructor() { }

  invitations: Invitation[];
  displayedColumns: string[] = ['No', 'visitDateTime', 'epamLocation', 'estimatedDuration', 'purpose' , 'poc'];
  upcomingDataSource: MatTableDataSource<Invitation>;
  pastDataSource: MatTableDataSource<Invitation>;

  ngOnInit(): void {
  }

  setInvitationTableDataSource(invitations: Invitation[]) {
   const pastVisits = invitations.filter( (invitation: Invitation) => 
      invitation.visitorResponse === CONSTANTS.YES && invitation.status === CONSTANTS.COMPLETED
    );
    const upcomingVisits = invitations.filter( (invitation: Invitation) => 
      invitation.visitorResponse === CONSTANTS.YES && (invitation.status === CONSTANTS.STARTED || invitation.status === CONSTANTS.NOT_STARTED)
    );

    this.pastDataSource = new MatTableDataSource<Invitation>(pastVisits);
    this.upcomingDataSource = new MatTableDataSource<Invitation>(upcomingVisits);
  }

}
