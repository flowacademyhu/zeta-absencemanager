import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl } from '@angular/material';

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
  


  constructor() { }

  ngOnInit() {
  }
  
  
}
