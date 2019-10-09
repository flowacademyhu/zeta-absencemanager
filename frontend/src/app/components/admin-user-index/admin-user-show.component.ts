import { Component, OnInit } from '@angular/core';

export interface UserElement {
  name: string;
  dob: number;
  position: string;
  supervisor: string;
  doe: number;
  email: string;
}

const Data: UserElement[] = [
  {name: 'jani', dob: 11011990, position: 'melós', supervisor: 'főnök', doe: 21102019, email: 'jani@janimail.com'}
]

@Component({
  selector: 'app-admin-user-show',
  templateUrl: './admin-user-show.component.html',
  styleUrls: ['./admin-user-show.component.css']
})
export class AdminUserShowComponent implements OnInit {

  displayedColumns: string[] = ['name', 'dob', 'position', 'supervisor', 'doe', 'email'];
  dataSource = Data;

  constructor() { }

  ngOnInit() {
  }

}
