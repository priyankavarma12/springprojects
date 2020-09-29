import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { VisitorService } from 'src/app/visitor/services/visitor.service';
import { AdminService } from 'src/app/admin/service/admin.service';
import { Router } from '@angular/router';
import { SharedService } from 'src/app/shared/shared.service';
import { Visitor } from 'src/app/models/visitor.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  formGroup : FormGroup
  userChoice : String
  constructor(private formBuilder : FormBuilder , private visitorService : VisitorService ,
              private adminService : AdminService, private sharedService: SharedService,
              private router : Router) { }

  ngOnInit(): void {
    localStorage.clear()
    this.formGroup = this.formBuilder.group({
      'email' : ['' , [Validators.required, Validators.email]],
      'password' : ['' , Validators.required]
    })
  }

  onLogin(){
    if(this.userChoice === 'visitor'){
     this.visitorService.authenticateVisitor(this.formGroup.value).subscribe((data :Visitor) => {
       // Need to check for login success, then only navigation should happen
        localStorage.setItem('visitorId' , data.visitorId.toString())
        this.router.navigate(['/visitor'])
      },
      error => {
        alert('Invalid Email or Password');
      })
    }
    else {
      this.adminService.authenticateAdmin(this.formGroup.value).subscribe((data : any)  =>  {
        // Need to check for login success, then only navigation should happen
        localStorage.setItem('adminId' , data.adminId.toString())
        this.router.navigate(['/admin']);
        this.sharedService.setAdmin(data);
      },
      error => {
        alert('Invalid Email or Password');
      })
    }

  }

  admin(){
    this.userChoice = "admin"
  }

  visitor(){
    this.userChoice = "visitor"
  }

}
