import { Component, OnInit } from '@angular/core';
import { User } from '../../../models/User.model';

const MOCK_DATA: any[] = [
  {id: 1, name: 'Hydrogen', begin: 1.0079, end: 'H', days: '3', type: 'absence', status: 'open', created_at: 1990-10-10, responsible: 'mockRes', reporter: 'Reporter', on_trial: true},

];

@Component({
  selector: 'app-admin-absences-index',
  templateUrl: './admin-absences-index.component.html', //'./absences-index.component.html',
  styleUrls: ['./admin-absences-index.component.css']  
})
  // templateUrl: './admin-user-edit-destroy-show.component.html',
  
export class AdminAbsencesIndexComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'begin', 'end', 'days', 'type', 'status', 'created_at', 'responsible', 'reporter', 'on_trial'];
  dataSource = MOCK_DATA;

  constructor() { }

  ngOnInit() {
  }

}
