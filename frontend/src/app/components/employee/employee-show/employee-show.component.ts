import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl } from '@angular/material';
import { User } from 'src/app/models/User.model';

 export interface UserElement {
  name: string;
  dob: string;
  position: string;
  supervisor: string;
  doe: string;
  email: string;
} 

const Data: UserElement = 
  {name: 'employee', dob: '1990-10-11', position: 'developer', supervisor: 'ceo', doe: '2019-10-10', email: 'emp@samplemail.com'},
 


@Component({
  selector: 'app-employee-show',
  templateUrl: './employee-show.component.html',
  styleUrls: ['./employee-show.component.css']
})
export class EmployeeShowComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
