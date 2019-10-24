import { Component, OnInit, OnDestroy } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { EmployeeAbsenceCreateModalComponent } from "../modals/employee-absence-create-modal/employee-absence-create-modal.component";
import { ActivatedRoute } from "@angular/router";
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { Absence } from "src/app/models/Absence.model";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { EmployeeAbsenceEditModalComponent } from "../modals/employee-absence-edit-modal/employee-absence-edit-modal.component";

@Component({
  selector: "app-employee-absences",
  templateUrl: "./employee-absences.component.html",
  styleUrls: ["./employee-absences.component.css"]
})
export class EmployeeAbsencesComponent implements OnInit, OnDestroy {
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

    const dialogRef = this.dialog.open(EmployeeAbsenceCreateModalComponent, dialogConfig);

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
    const dialogRef = this.dialog.open(EmployeeAbsenceEditModalComponent, {
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
