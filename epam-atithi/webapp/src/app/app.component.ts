import { Component } from '@angular/core';
import { SharedService } from './shared/shared.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(public sharedService: SharedService , private router : Router) {}
  
  title = 'epam-atithi';

  loadHomePage(){
    let visitorId = localStorage.getItem('visitorId')
    let adminId = localStorage.getItem('adminId')
    if(adminId !== null ){
      this.router.navigate(['admin'])
    }
    else if(visitorId !== null) {
      this.router.navigate(['visitor'])
    }
    else  {
      this.router.navigate(['login'])
    }
  }
}
