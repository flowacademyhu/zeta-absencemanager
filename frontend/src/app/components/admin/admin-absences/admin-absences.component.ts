import { Component, OnInit } from '@angular/core';
import { Absence } from 'src/app/models/Absence.model';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';


@Component({
  selector: 'app-admin-absences',
  templateUrl: './admin-absences.component.html',
  styleUrls: ['./admin-absences.component.css']  
})
  
export class AdminAbsencesComponent implements OnInit {
  private _unsubscribe$: Subject<boolean> = new Subject<boolean>();
  displayedColumns: string[] = ['id', 'begin', 'end', 'days', 'type', 'status', 'created_at', 'reporter', 'assignee', 'delete'];
  absencesList: Absence[];

  constructor(public api: ApiCommunicationService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((absences: any) => {
        this.absencesList = absences.adminAbsenceList;
        console.log(this.absencesList);
      });
  }

}
