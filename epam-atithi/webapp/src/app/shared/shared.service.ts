import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class SharedService {

  private _type: string = 'light';
  private _message: string = '';
  private _loading: boolean = false;
  private _admin: any;

  constructor(private _snackBar: MatSnackBar) { }

  get type(): string {
    return this._type;
  }

  get message(): string {
    return this._message;
  }

  displayAlert(message: string) {
    this._message = message;

    this._snackBar.open(message, '', {
      duration: 5000,
      verticalPosition: 'top',
      panelClass: 'custom-snack-bar'
    });
  }

  hideAlert() {
    this._message = '';
  }
  
  get loading() {
    return this._loading;
  }

  set loading(load) {
    this._loading = load;
  }
  
  getAdmin() {
    return this._admin;
  }

  setAdmin(data) {
    this._admin = data;
  }

}
