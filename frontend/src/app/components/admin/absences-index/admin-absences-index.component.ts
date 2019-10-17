import { Component, OnInit } from '@angular/core';
import { Absence } from 'src/app/models/Absence.model';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';
import { User } from '../../../models/User.model';

const MOCK_DATA: any[] = [
  {id: 1, begin: 1.0079, end: 'H', days: '3', type: 'absence', status: 'open', created_at: 1990-10-10, responsible: 'mockRes', reporter: 'Reporter', on_trial: true},

];

@Component({
  selector: 'app-admin-absences-index',
  templateUrl: './admin-absences-index.component.html', //'./absences-index.component.html',
  styleUrls: ['./admin-absences-index.component.css']  
})
  // templateUrl: './admin-user-edit-destroy-show.component.html',
  
export class AdminAbsencesIndexComponent implements OnInit {
  displayedColumns: string[] = ['id', 'begin', 'end', 'days', 'type', 'status', 'created_at', 'reporter', 'assignee'];
  dataSource: Absence[];

  constructor(public api: ApiCommunicationService) { }

  ngOnInit() {
    this.api.adminAbsence().getAbsences().subscribe(absences => {this.dataSource = absences; console.log(this.dataSource);});
  }

}
