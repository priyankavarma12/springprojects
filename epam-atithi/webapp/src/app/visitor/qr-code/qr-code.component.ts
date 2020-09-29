import { Component, OnInit, Input } from '@angular/core';
import { VisitorService } from '../services/visitor.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-qr-code',
  templateUrl: './qr-code.component.html',
  styleUrls: ['./qr-code.component.scss']
})
export class QrCodeComponent implements OnInit {
  invitationId : any;
  imageToShow : any;
  constructor(private visitorService : VisitorService , private route : ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.invitationId = params.get('invitationId');
    });
    this.visitorService.getQrCode(this.invitationId).subscribe(data => {
          this.createImageFromBlob(data);
    })
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
       this.imageToShow = reader.result;
    }, false);
 
    if (image) {
       reader.readAsDataURL(image);
    }
 }
}
