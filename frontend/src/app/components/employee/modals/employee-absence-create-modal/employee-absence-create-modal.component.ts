import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/api-communication.service";

@Component({
  selector: "app-employee-absence-create-modal",
  templateUrl: "./employee-absence-create-modal.component.html",
  styleUrls: ["./employee-absence-create-modal.component.css"]
})
export class EmployeeAbsenceCreateModalComponent implements OnInit {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<EmployeeAbsenceCreateModalComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.types = Absence.enumSelector(AbsenceType);
    this.createAbsenceForm = new FormGroup({
      type: new FormControl("", Validators.required),
      summary: new FormControl(""),
      start: new FormControl("", Validators.required),
      end: new FormControl("", Validators.required)
    });
  }

  public OnSubmit(createAbsenceFormValue): void {
    if (this.createAbsenceForm.valid) {
      let newAbsence = new Absence(
        createAbsenceFormValue.type,
        createAbsenceFormValue.summary,
        createAbsenceFormValue.start,
        createAbsenceFormValue.end
      );
      var month = newAbsence.begin.getUTCMonth() + 1;
      var day = newAbsence.begin.getUTCDate() + 1;
      var year = newAbsence.begin.getUTCFullYear();
      newAbsence.begin = [];
      newAbsence.begin[0] = year;
      newAbsence.begin[1] = month;
      newAbsence.begin[2] = day;

      var month = newAbsence.end.getUTCMonth() + 1;
      var day = newAbsence.end.getUTCDate() + 1;
      var year = newAbsence.end.getUTCFullYear();
      newAbsence.end = [];
      newAbsence.end[0] = year;
      newAbsence.end[1] = month;
      newAbsence.end[2] = day;
      /* newAbsence.begin = (newAbsence.begin as Date)
        .toISOString()
        .split("T")[0]
        .split("-");
      for (let i = 0; i < 3; i++) {
        newAbsence.begin[i] = parseInt(newAbsence.begin[i]);
      }
      newAbsence.end = (newAbsence.end as Date)
        .toISOString()
        .split("T")[0]
        .split("-");
      for (let i = 0; i < 3; i++) {
        newAbsence.end[i] = parseInt(newAbsence.end[i]);
      } */
      console.log(newAbsence);
      this.api
        .absence()
        .createAbsence(newAbsence)
        .subscribe(
          data => {
            this.dialogRef.close();
          },
          err => {
            this.error = err.error.message;
          }
        );
    }
  }

  public onCancel(): void {
    this.dialogRef.close();
  }
}
