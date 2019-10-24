import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import * as moment from "moment";

@Component({
  selector: "app-employee-absence-create-modal",
  templateUrl: "./employee-absence-create-modal.component.html",
  styleUrls: ["./employee-absence-create-modal.component.css"]
})
export class EmployeeAbsenceCreateModalComponent implements OnInit {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;
  private duration = 0;
  private dates = [false, false];

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
      end: new FormControl("", Validators.required),
      duration: new FormControl(this.duration)
    });
  }

  public countDuration(): number {
    this.duration = 0;
    if (this.dates[0] === true && this.dates[1] === true) {
      var begin = moment(this.createAbsenceForm.controls["start"].value);
      var end = moment(this.createAbsenceForm.controls["end"].value);
      this.duration = Math.floor(moment.duration(end.diff(begin)).asDays()) + 1;
    }
    this.createAbsenceForm.controls["duration"].setValue(this.duration);
    return this.duration;
  }

  changeHandler(event): number {
    if (event.targetElement.id === "start") {
      this.dates[0] = true;
    } else {
      this.dates[1] = true;
      console.log(this.dates);
    }
    if (this.dates[0] === true && this.dates[1] === true) {
      this.countDuration();
    }
    return this.duration;
  }

  public OnSubmit(createAbsenceFormValue): void {
    if (this.createAbsenceForm.valid) {
      let newAbsence = new Absence(
        createAbsenceFormValue.type,
        createAbsenceFormValue.summary,
        createAbsenceFormValue.start,
        createAbsenceFormValue.end,
        createAbsenceFormValue.duration
      );

      if (newAbsence.duration === 0 || newAbsence.duration == null) {
        var begin = moment(newAbsence.begin);
        var end = moment(newAbsence.end);
        var duration =
          Math.floor(moment.duration(end.diff(begin)).asDays()) + 1;
        newAbsence.duration = duration;
      }

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
