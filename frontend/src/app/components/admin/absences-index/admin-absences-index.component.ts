import { Component, OnInit } from '@angular/core';

const MOCK_DATA: any[] = [
  {id: 1, name: 'Hydrogen', begin: 1.0079, end: 'H', days: '3', type: 'absence', status: 'open', created_at: 1990-10-10, responsible: 'mockRes', reporter: 'Reporter', on_trial: true},

];

@Component({
  selector: 'app-absences-index',
  templateUrl: './absences-index.component.html',
  styleUrls: ['./absences-index.component.css']
})
export class AdminAbsencesIndexComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'begin', 'end', 'days', 'type', 'status', 'created_at', 'responsible', 'reporter', 'on_trial'];
  dataSource = MOCK_DATA;

  constructor() { }

  ngOnInit() {
  }

}
