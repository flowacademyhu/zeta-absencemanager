import { Component, OnInit } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { AbsencesCreateComponent } from "../absences-create/absences-create.component";
import { ActivatedRoute } from "@angular/router";
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { Absence } from "src/app/models/Absence.model";
import { ApiCommunicationService } from "src/app/services/ApiCommunication.service";
import { AbsenceShowEditComponent } from "../absence-show-edit/absence-show-edit.component";

@Component({
  selector: "app-emp-absences-index",
  templateUrl: "./emp-absences-index.component.html",
  styleUrls: ["./emp-absences-index.component.css"]
})
export class EmpAbsencesIndexComponent implements OnInit {
  displayedColumns: string[] = [
    "id",
    "begin",
    "end",
    "days",
    "type",
    "summary",
    "status",
    "edit"
  ];
  private _unsubscribe$: Subject<boolean> = new Subject<boolean>();
  absences: Absence[];

  constructor(
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private api: ApiCommunicationService
  ) {}

  ngOnInit() {
    this.route.data
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((data: any) => {
        this.absences = data.absences;
      });
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40vw";

    dialogConfig.data = {};

    const dialogRef = this.dialog.open(AbsencesCreateComponent, dialogConfig);

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(data2 => {
        this.api
          .absence()
          .getAbsences()
          .subscribe(data => {
            this.absences = data;
          });
      });
  }

  editAbsence(id: number): void {
    const dialogRef = this.dialog.open(AbsenceShowEditComponent, {
      data: { absence: this.absences.filter(absence => absence.id === id)[0] }
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(data2 => {
        this.api
          .absence()
          .getAbsences()
          .subscribe(data => {
            this.absences = data;
          });
      });
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }
}
