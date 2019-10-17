import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { CreateUserComponent } from 'src/app/modals/create-user/create-user.component';
import { User } from 'src/app/models/User.model';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';
import { Group } from 'src/app/models/Group.model';


@Component({
  selector: 'app-admin-user-show',
  templateUrl: './admin-user-show.component.html',
  styleUrls: ['./admin-user-show.component.css']
})
export class AdminUserShowComponent implements OnInit {
  displayedColumns: string[] = ['name', 'dob', 'position', 'supervisor', 'doe', 'email'];
  dataSource; // --> filter

  userData: User;
  usersList: User[];

  constructor(private api: ApiCommunicationService, public dialog: MatDialog) {}

  createUser(): void {
    const dialogRef = this.dialog.open(CreateUserComponent, {
      data: {user: this.userData}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.userData = result;
      this.userData.isOnTrial = true;
      this.dateConverter();
      console.log(this.userData);      
      this.api.user().createUser(this.userData).subscribe(u => console.log("created:" + u));
    });
    
  }

  

  ngOnInit() {
    this.api.user().getUsers().subscribe(users => {
      this.usersList = users;
      this.dataSource = new MatTableDataSource(this.usersList);
      console.log(this.usersList);
    })
  }
  

  private dateConverter() {
    this.userData.dateOfEndTrial = (this.userData.dateOfEndTrial as Date).toISOString().split("T")[0].split("-");
    this.userData.dateOfBirth = (this.userData.dateOfBirth as Date).toISOString().split("T")[0].split("-");
    this.userData.dateOfEntry = (this.userData.dateOfEntry as Date).toISOString().split("T")[0].split("-"); 
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfEntry[i] = parseInt(this.userData.dateOfEntry[i]);
    }
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfBirth[i] = parseInt(this.userData.dateOfBirth[i]);
    }
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfEndTrial[i] = parseInt(this.userData.dateOfEndTrial[i]);
    }
  }
  
}
