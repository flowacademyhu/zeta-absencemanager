import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from "@angular/material";
import { AdminAbsenceCreateComponent } from 'src/app/modals/admin-absence-create/admin-absence-create.component';
import { Absence } from 'src/app/models/Absence.model';

const MOCK_DATA: any[] = [
  { id: 1, name: 'Hydrogen', begin: 1.0079, end: 'H', days: '3', type: 'absence', status: 'open', created_at: 1990 - 10 - 10, responsible: 'mockRes', reporter: 'Reporter', on_trial: true },

];

@Component({
  selector: 'app-absences-index',
  templateUrl: './absences-index.component.html',
  styleUrls: ['./absences-index.component.css']
})
export class AbsencesIndexComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'begin', 'end', 'days', 'type', 'status', 'created_at', 'responsible', 'reporter', 'on_trial'];
  dataSource = MOCK_DATA;
  newAbsence: Absence;

  constructor(private dialog: MatDialog) { }

  ngOnInit() {
  }

  openDialog() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = '40vw';

    dialogConfig.data = {

    };

    // this.dialog.open(AdminAbsenceCreateComponent, dialogConfig);

    const dialogRef = this.dialog.open(AdminAbsenceCreateComponent, dialogConfig);

    dialogRef.afterClosed().subscribe(
      data => console.log("Dialog output:", data)
    );
  }

}
