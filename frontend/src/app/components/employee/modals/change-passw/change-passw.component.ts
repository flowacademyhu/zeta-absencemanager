import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { SessionService } from 'src/app/services/session.service';
import { User } from '../../../../models/User.model';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';


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
    public dialogRef: MatDialogRef<ChangePasswComponent>,
    @Inject(MAT_DIALOG_DATA)  public data: any,
    public sessionService: SessionService,  
    public api: ApiCommunicationService
  ) { }
  
  ngOnInit() {
   this.userObject = this.sessionService.getUserData();
   this.id = this.userObject.id;
  }
  
  sendPassw() {
   this.api.employee().changePassword(this.id, this.firstPass, this.secondPass).subscribe(data => {
     this.dialogRef.close();
   })
  }
  
  onCancel(): void {
    this.dialogRef.close();
  }
   
} 
