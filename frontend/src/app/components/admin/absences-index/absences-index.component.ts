import { Component, OnInit } from '@angular/core';
import { Absence } from 'src/app/models/Absence.model';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';

const MOCK_DATA: any[] = [
  {id: 1, name: 'Hydrogen', begin: 1.0079, end: 'H', days: '3', type: 'absence', status: 'open', created_at: 1990-10-10, responsible: 'mockRes', reporter: 'Reporter', on_trial: true},

];

@Component({
  selector: 'app-absences-index',
  templateUrl: './absences-index.component.html',
  styleUrls: ['./absences-index.component.css']
})
export class AbsencesIndexComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'begin', 'end', 'days', 'type', 'status', 'created_at', 'responsible', 'reporter', 'on_trial'];
  dataSource: Absence[];

  constructor(public api: ApiCommunicationService) { }

  ngOnInit() {
    this.api.adminAbsence().getAbsences().subscribe(absences => {this.dataSource = absences; console.log(this.dataSource);});
  }

}
