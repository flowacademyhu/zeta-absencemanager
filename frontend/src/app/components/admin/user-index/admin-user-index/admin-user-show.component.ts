import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { CreateUserComponent } from 'src/app/modals/create-user/create-user.component';

export interface UserElement {
  name: string;
  dob: string;
  position: string;
  supervisor: string;
  doe: string;
  email: string;
}

export interface userData {
  firstName : string,
  lastName : string,
  dateOfBirth : Date,
  dateOfEntry : Date,
  position : string,
  email : string,
  numberOfChildren: number,
  group: any
  };

const Data: UserElement[] = [
  {name: 'employee', dob: '1990-10-11', position: 'developer', supervisor: 'ceo', doe: '2019-10-10', email: 'emp@samplemail.com'},
  {name: 'employeenr2', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'}
]

@Component({
  selector: 'app-admin-user-show',
  templateUrl: './admin-user-show.component.html',
  styleUrls: ['./admin-user-show.component.css']
})
export class AdminUserShowComponent implements OnInit {

  displayedColumns: string[] = ['name', 'dob', 'position', 'supervisor', 'doe', 'email'];
  dataSource = new MatTableDataSource(Data); // --> filter

  //TODO: change 'userData' to USER
  userData : userData = {
  firstName: '',
  lastName: '',
  dateOfBirth: null,
  dateOfEntry: null,
  position: '',
  email: '',
  numberOfChildren: null,
  group: null
  };

  constructor(public dialog: MatDialog) {}

  createUser(): void {
    const dialogRef = this.dialog.open(CreateUserComponent, {
      data: {user: this.userData}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.userData = result;
      console.log(this.userData);
    });
  }

  ngOnInit() {
  }
  
  
}
