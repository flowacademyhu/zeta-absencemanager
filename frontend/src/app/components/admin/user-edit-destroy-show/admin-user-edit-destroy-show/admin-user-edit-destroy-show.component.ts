import { Component, OnInit, Input, ViewChild } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl, MatPaginator } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminAbsencesIndexComponent } from '../../absences-index/admin-absences-index.component';



export interface UserElement {
  name: string;
  dob: string;
  position: string;
  supervisor: string;
  doe: string;
  email: string;
}

const Data: UserElement[] = [
  {name: 'employee', dob: '1990-10-11', position: 'developer', supervisor: 'ceo', doe: '2019-10-10', email: 'emp@samplemail.com'},
  {name: 'employeenr2', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'},
  {name: 'admUsrEditDestShow', dob: '1995-10-11', position: 'staff', supervisor: 'ceo', doe: '2018-10-10', email: 'employee@samplemail.com'}
]


@Component({
  selector: 'app-admin-user-edit-destroy-show',
  templateUrl: './admin-user-edit-destroy-show.component.html',
  styleUrls: ['./admin-user-edit-destroy-show.component.css']
})
export class AdminUserEditDestroyShowComponent implements OnInit {

  displayedColumns: string[] = ['name', 'dob', 'position', 'supervisor', 'doe', 'email'];
  dataSource = new MatTableDataSource(Data); 

  @ViewChild(MatPaginator, {static: true}) paginator : MatPaginator;

  constructor(public dialog: MatDialog) { }

  openDialog(){
    this.dialog.open(AdminAbsencesIndexComponent);
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
  }

}


