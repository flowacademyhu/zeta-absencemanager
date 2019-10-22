import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/ApiCommunication.service";
import * as moment from "moment";

@Component({
  selector: "app-absences-create",
  templateUrl: "./absences-create.component.html",
  styleUrls: ["./absences-create.component.css"]
})
export class AbsencesCreateComponent implements OnInit {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;
  duration = 0;

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<AbsencesCreateComponent>,
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

  private dates = [false, false];
  changeHandler(event): number {
    console.log(event.targetElement.id);
    if (event.targetElement.id === "start") {
      this.dates[0] = true;
      console.log(this.dates);
    } else {
      this.dates[1] = true;
      console.log(this.dates);
    }
    if (this.dates[0] === true && this.dates[1] === true) {
      this.countDuration();
    }
    /* this.dates[n] = true;
    console.log(this.dates[n]);
    if (this.dates[0] === true && this.dates[1] === true) {
      this.countDuration(event);
    } */
    return 5; //this.duration;
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
      console.log(newAbsence);
      console.log(newAbsence.duration);

      newAbsence.begin = (newAbsence.begin as Date)
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
      }

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
