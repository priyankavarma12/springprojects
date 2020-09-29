import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Invitation } from 'src/app/models/invitation.model';
import { Router } from '@angular/router';
import { Visitor } from 'src/app/models/visitor.model';
import { AdminService } from '../service/admin.service';
import { SharedService } from 'src/app/shared/shared.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {

  constructor(private router: Router, private adminService: AdminService, private sharedService: SharedService) { }

  displayedColumns: string[] = ['No', 'name', 'nationalId', 'email', 'primaryPhone', 'activated', 'verificationEmailSent'];
  dataSource;// = new MatTableDataSource<Visitor>(VISITORS);

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit() {
    let adminId = localStorage.getItem('adminId')
    this.adminService.getVisitorsByAdminId(adminId).subscribe( (visitors: any) => {
      this.dataSource = new MatTableDataSource<Visitor>(visitors);
      this.dataSource.paginator = this.paginator;
    } );
  }

  navigateToVisitorDetails(visitor: Visitor) {
    this.router.navigate( 
      ['/visitor-details', {'visitorId': visitor.visitorId}]
    );
  }

  navigateToNewVisitorRegistration() {
    this.router.navigate(['/visitor-registration']);
  }

}
