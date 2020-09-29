import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AdminService } from '../service/admin.service';
import { Invitation } from 'src/app/models/invitation.model';
import { SharedService } from 'src/app/shared/shared.service';
import { Visitor } from 'src/app/models/visitor.model';
import { MAT_DATE_LOCALE } from '@angular/material/core';

@Component({
  selector: 'app-invitation',
  templateUrl: './invitation.component.html',
  styleUrls: ['./invitation.component.scss'],
  providers: [{ provide: MAT_DATE_LOCALE, useValue: 'en-GB' }]
})
export class InvitationComponent implements OnInit {

  invitationForm: FormGroup;
  visitor: Visitor;
  meridians = [{value : 'AM' } , {value : 'PM'}];

  constructor(public dialogRef: MatDialogRef<InvitationComponent>, @Inject(MAT_DIALOG_DATA) public data: any,
              private formBuilder: FormBuilder, private adminService: AdminService, public sharedService: SharedService) {
                this.visitor = this.data.visitor;
              }

  ngOnInit(): void {
    this.invitationForm = this.formBuilder.group({
      epamLocation: ['', [Validators.required]],
      estimatedDuration: ['', [Validators.required]],
      inTime: [''],
      invitationId: [''],
      outTime: [''],
      poc: ['', Validators.required],
      purpose: ['', [Validators.required]],
      timeHrs: [0, [Validators.required]],
      timeMins: [0, [Validators.required]],
      meridian: ['AM', [Validators.required]],
      status: [''],
      visitCompleted: [false],
      visitDateTime: [new Date(), [Validators.required]],
      visitorId: [''],
      visitorLocation: ['', [Validators.required]],
      visitorResponse: ['']
    });
  }

  submitInvitaion() {
    this.invitationForm.get('visitorId').setValue(this.visitor.visitorId);
    const invitation: Invitation = this.invitationForm.value as Invitation;
    invitation.visitDateTime = this.invitationForm.value.visitDateTime.toLocaleDateString() + ' ' + invitation.timeHrs + ':' + invitation.timeMins + ' ' + invitation.meridian;
    // for time being
    delete invitation.timeHrs;
    delete invitation.timeMins;
    delete invitation.meridian;
    // for time being
    this.sharedService.loading = true;
    this.adminService.createInvitation(this.visitor.adminId, invitation).subscribe(value=>{
      this.sharedService.loading = false;
      this.sharedService.displayAlert('Invitation ' + value);
    })
  }

}
