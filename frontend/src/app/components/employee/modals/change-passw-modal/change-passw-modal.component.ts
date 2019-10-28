import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SessionService } from 'src/app/services/session.service';
import { User } from '../../../../models/User.model';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';


@Component({
  selector: 'app-change-passw',
  templateUrl: './change-passw-modal.component.html',
  styleUrls: ['./change-passw-modal.component.css']
})
export class ChangePasswModalComponent implements OnInit {
  
  public firstPassword: string;
  public secondPassword: string;
  public userObject: User;
  public id : number;  
  public passwordToCompare : string;
  public oldPassword: string;
  public error: boolean;
  
  constructor(
    public dialogRef: MatDialogRef<ChangePasswModalComponent>,
    @Inject(MAT_DIALOG_DATA)  public data: any,
    public sessionService: SessionService,  
    public api: ApiCommunicationService
  ) { }
  
  ngOnInit() {
   this.userObject = this.sessionService.getUserData();
   this.id = this.userObject.id;
  }   

  sendPassw() {
    this.api.employee().changePassw(this.firstPassword, this.secondPassword, this.oldPassword).subscribe(data => {
    this.dialogRef.close();
    }, error => {
    this.error = true;
    })  
  }
  
  onCancel(): void {
    this.dialogRef.close();
  }

  checkPasswords(): boolean {
    return this.firstPassword === this.secondPassword;
  }

} 
