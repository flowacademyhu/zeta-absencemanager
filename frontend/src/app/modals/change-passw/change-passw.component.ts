import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { HttpClient } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { User } from '../../models/User.model';


@Component({
  selector: 'app-change-passw',
  templateUrl: './change-passw.component.html',
  styleUrls: ['./change-passw.component.css']
})
export class ChangePasswComponent implements OnInit {

  public firstPass: any;
  public secondPass: any;
  public userObject: User;

  public passwords = {first: this.firstPass, second: this.secondPass}

  constructor(
    public http: HttpClient,
    public dialogRef: MatDialogRef<ChangePasswComponent>,
    @Inject(MAT_DIALOG_DATA)  public data: any,
    public sessionService: SessionService
  ) { }

  ngOnInit() {
    // this.userObject = this.sessionService.getUserData();
    // console.log(this.userObject + " itt kéne lennie");
  }

  sendPassw(passwords: Object) {
    this.userObject = this.sessionService.getUserData();
    console.log(this.userObject);
    //return this.http.post("localhost:8080/", this.passwords);
  }

  onCancel(): void {
    this.dialogRef.close();
  }

}
