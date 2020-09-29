import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Visitor } from 'src/app/models/visitor.model';
import { VisitorService } from '../services/visitor.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.scss']
})
export class EditProfileComponent implements OnInit {
  visitorData : Visitor
  formGroup : FormGroup
  countryCode = []
  constructor(private formBuilder : FormBuilder , private visitorService : VisitorService , private http : HttpClient) { }

  ngOnInit(): void {
    this.create()
    this.visitorService.getVisitorDetails().subscribe((data : Visitor) => {
      this.visitorData = data
      this.formGroup.get('email').setValue(data.email)
      this.formGroup.get('firstName').setValue(data.firstName)
      this.formGroup.get('lastName').setValue(data.lastName)
      this.formGroup.get('nationalId').setValue(data.nationalId)
      this.formGroup.get('companyName').setValue(data.companyName)
      this.formGroup.get('codePrimaryPhone').setValue(data.primaryCountryCode)
      this.formGroup.get('primaryPhone').setValue(data.primaryPhNumber)
      this.formGroup.get('secondaryPhone').setValue(data.secondaryPhNumber)
      this.formGroup.get('codeSecondaryPhone').setValue(data.secondaryCountryCode)
    })
    this.http.get('../assets/country-code.json').subscribe((data : any) => {
      this.countryCode = data
    })
  }

  create(){
    this.formGroup = this.formBuilder.group({
      'email': [{value : '', disabled : true}],
      'firstName': [{value : '' , disabled : true}],
      'lastName': [{value : '' , disabled : true}],
      'nationalId': ['', Validators.required],
      'companyName': ['' , Validators.required],
      'codePrimaryPhone' : [null , Validators.required],
      'primaryPhone': ['', [Validators.required , Validators.minLength(10), Validators.maxLength(10)]],
      'secondaryPhone': ['',[Validators.minLength(10), Validators.maxLength(10)]],
      'codeSecondaryPhone' : [null],
    });
  }

  onUpdate(){
    this.visitorData.nationalId = this.formGroup.get('nationalId').value
    this.visitorData.companyName = this.formGroup.get('companyName').value
    this.visitorData.primaryCountryCode = this.formGroup.get('codePrimaryPhone').value
    this.visitorData.primaryPhNumber = this.formGroup.get('primaryPhone').value
    this.visitorData.secondaryPhNumber = this.formGroup.get('secondaryPhone').value
    this.visitorData.secondaryCountryCode = this.formGroup.get('codeSecondaryPhone').value
    this.visitorService.updateVisitorDetails(this.visitorData).subscribe(data => {
    })

   }

}

//const ELEMENT_DATA : Visitor = {visitorId : 1 , adminId : 1 , primaryCountryCode : '91' , secondaryCountryCode : '91' , firstName : 'ABC' , lastName : 'XYZ' , primaryPhNumber : '987654123' , secondaryPhNumber : '987456123' , email : 'abc@gmail.com' , nationalId  : '' , activated : true , verificationEmailSent : true}

