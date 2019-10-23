import { Component, OnInit, Inject } from "@angular/core";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { FormGroup, FormControl } from "@angular/forms";
import { AbsenceType, Absence } from "src/app/models/Absence.model";
import { ActivatedRoute, Params } from "@angular/router";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";

@Component({
  selector: "app-employee-absence-edit-modal",
  templateUrl: "./employee-absence-edit-modal.component.html",
  styleUrls: ["./employee-absence-edit-modal.component.css"]
})
export class EmployeeAbsenceEditModalComponent implements OnInit {
  private createAbsenceForm: FormGroup = new FormGroup({});
  private types;
  private error: string;
  private absence: Absence;
  /* private absence: Absence = new Absence(
    <AbsenceType>"ABSENCE",
    "valami",
    new Date(2019, 10, 13),
    new Date(2019, 10, 17)
  ); */
  private update: boolean = false;
  private message = "Modify";
  private _unsubscribe$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<EmployeeAbsenceEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.api
      .absence()
      .getAbsence(this.data.absence.id)
      .subscribe(
        data => {
          this.absence = data;
          this.absence.begin = new Date(
            data.begin[0],
            data.begin[1],
            data.begin[2]
          );
          this.absence.end = new Date(data.end[0], data.end[1], data.end[2]);
          this.types = Absence.enumSelector(AbsenceType);
          this.createAbsenceForm = new FormGroup({
            type: new FormControl(this.absence.type),
            summary: new FormControl(this.absence.summary),
            start: new FormControl(this.absence.begin),
            end: new FormControl(this.absence.end)
          });
        },
        err => {
          this.error = err.error.message;
        }
      );
  }

  public OnSubmit(createAbsenceFormValue): void {
    if (this.update) {
      this.update = !this.update;
      this.message = "Modify";
      if (this.createAbsenceForm.valid) {
        (this.absence.type = createAbsenceFormValue.type),
          (this.absence.summary = createAbsenceFormValue.summary),
          (this.absence.begin = createAbsenceFormValue.start),
          (this.absence.end = createAbsenceFormValue.end);
        this.api
          .absence()
          .updateAbsence(this.absence.id, this.absence)
          .pipe(takeUntil(this._unsubscribe$))
          .subscribe(
            data => {},
            err => {
              this.error = err.error.message;
            }
          );
      }
    } else {
      this.update = !this.update;
      this.message = "Save";
    }
  }

  public onCancel(): void {
    this.dialogRef.close();
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }
}
