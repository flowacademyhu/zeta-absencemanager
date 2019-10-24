import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { User } from '../../../../models/User.model';
import { PasswordService } from 'src/app/services/password.service';


@Component({
  selector: 'app-change-passw',
  templateUrl: './change-passw.component.html',
  styleUrls: ['./change-passw.component.css']
})
export class ChangePasswComponent implements OnInit {
  
  public firstPass: any;
  public secondPass: any;
  public userObject: User;
  public id : number;  
  
  constructor(
    public http: HttpClient,
    public dialogRef: MatDialogRef<ChangePasswComponent>,
    @Inject(MAT_DIALOG_DATA)  public data: any,
    public sessionService: SessionService,
    public passwordService: PasswordService
  ) { }
  
  ngOnInit() {
   this.userObject = this.sessionService.getUserData();
   this.id = this.userObject.id;
  }
  
  sendPassw() {
   return this.passwordService.sendPassword(this.firstPass, this.secondPass, this.id); 
  }
  
  onCancel(): void {
    this.dialogRef.close();
  }
   
} 
