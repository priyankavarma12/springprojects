import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Visitor } from 'src/app/models/visitor.model';
import { switchMap } from 'rxjs/operators';
import { VisitorService } from 'src/app/visitor/services/visitor.service';
import { of, Observable } from 'rxjs';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Invitation } from 'src/app/models/invitation.model';
import { InvitationComponent } from 'src/app/admin/invitation/invitation.component';
import { SharedService } from 'src/app/shared/shared.service';
import { AdminService } from 'src/app/admin/service/admin.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-visitor-details',
  templateUrl: './visitor-details.component.html',
  styleUrls: ['./visitor-details.component.scss']
})
export class VisitorDetailsComponent implements OnInit {

  visitorRegForm: FormGroup;
  defaultFormValue: any;
  visitor: Visitor;
  visitor$: Observable<any>;

  showEvents: boolean = true;
  countryCode: any = [];
  invitations: Invitation[];
  invitationDialogRef: MatDialogRef<InvitationComponent>;

  constructor(private http : HttpClient ,private formBuilder: FormBuilder, private route: ActivatedRoute, private visitorService: VisitorService,
              private adminService: AdminService, private sharedService: SharedService, private router: Router,
              private dialog: MatDialog) { }

  ngOnInit(): void {
    this.createForm();
    this.http.get('../../assets/country-code.json').subscribe(data => {
      this.countryCode = data;
    });

    this.visitor$ = this.route.paramMap.pipe(
      switchMap(params => {
        let visitorId = params.get('visitorId');
        if(!visitorId) {
          this.showEvents = false;
          return of({});
        }
        return this.visitorService.getVisitorById(+visitorId);
      })
    );

    this.visitor$.subscribe((visitor: Visitor) => {
      if( visitor && visitor.visitorId ) {
        this.visitor = visitor;
        this.visitorRegForm.patchValue(visitor);
        this.defaultFormValue = this.visitorRegForm.value;
        this.invitations = visitor.invitations;
      }
    });
  }

  createForm() {
    let emailregex: RegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    this.visitorRegForm = this.formBuilder.group({
      'email': ['', [Validators.required, Validators.pattern(emailregex)]],
      'firstName': ['', Validators.required],
      'lastName': ['', Validators.required],
      'nationalId': ['', Validators.required],
      'companyName': ['', Validators.required],
      'primaryCountryCode': ['', Validators.required],
      'primaryPhNumber': ['', [Validators.required, Validators.minLength(10), Validators.maxLength(10)]],
      'secondaryCountryCode': [''],
      'secondaryPhNumber': ['', [Validators.minLength(10), Validators.maxLength(10)]],
    });

    this.defaultFormValue = this.visitorRegForm.value;
  }

  onSubmit() {
    if(this.showEvents) {
      this.updateVisitor();
    }
    else {
      this.createNewVisitor();
    }
  }

  createNewVisitor() {
    const newVisitor: Visitor = this.visitorRegForm.value as Visitor;
    this.sharedService.loading = true;
    const adminId = this.sharedService.getAdmin().adminId;
    this.adminService.createVisitor(adminId, newVisitor).subscribe(value=>{
      this.sharedService.loading = false;
      this.sharedService.displayAlert('Visitor ' + value);
      this.resetForm();
    });
  }

  updateVisitor() {
    const updateVisitor: Visitor = this.visitorRegForm.value as Visitor;
    updateVisitor.visitorId = this.visitor.visitorId;
    updateVisitor.adminId = this.visitor.adminId;
    this.sharedService.loading = true;
    this.visitorService.updateVisitorDetails(updateVisitor).subscribe(value=>{
      this.sharedService.loading = false;
      this.sharedService.displayAlert('Visitor details updated');
    });
  }

  createNewInvitation() {
    this.invitationDialogRef = this.dialog.open(InvitationComponent ,{minHeight:'400px', minWidth:'300px', width: '40%', data: {visitor: this.visitor}});
  }

  resetForm() {
    this.visitorRegForm.patchValue(this.defaultFormValue);
    this.visitorRegForm.markAsUntouched();
  }

  backToAdminHomePage() {
    this.router.navigate(['/admin']);
  }

}
