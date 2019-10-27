import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { User } from "src/app/models/User.model";
import * as moment from "moment";

@Component({
  selector: "app-admin-absence-create-modal",
  templateUrl: "./admin-absence-create-modal.component.html",
  styleUrls: ["./admin-absence-create-modal.component.css"]
})
export class AdminAbsenceCreateModalComponent implements OnInit {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;
  private leaders: User[];
  private users: User[];
  private duration;
  private dates = [false, false];

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<AdminAbsenceCreateModalComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.api
      .user()
      .getLeaders()
      .subscribe(data => {
        this.leaders = data;
      });
    this.api
      .user()
      .getUsers()
      .subscribe(data => {
        this.users = data;
      });
    this.types = Absence.enumSelector(AbsenceType);
    this.createAbsenceForm = new FormGroup({
      type: new FormControl("", Validators.required),
      summary: new FormControl(""),
      begin: new FormControl("", Validators.required),
      end: new FormControl("", Validators.required),
      reporter: new FormControl("", Validators.required),
      assignee: new FormControl("", Validators.required),
      duration: new FormControl(this.duration, Validators.required)
    });
  }

  public countDuration(): number {
    var begin = moment(this.createAbsenceForm.controls["begin"].value);
    var end = moment(this.createAbsenceForm.controls["end"].value);
    this.duration = Math.floor(moment.duration(end.diff(begin)).asDays()) + 1;
    this.createAbsenceForm.controls["duration"].setValue(this.duration);
    return this.duration;
  }

  changeHandler(event): number {
    console.log(event);
    if (event.targetElement.id === "begin") {
      this.dates[0] = true;
    } else {
      this.dates[1] = true;
    }
    if (this.dates[0] === true && this.dates[1] === true) {
      this.countDuration();
    }
    console.log(this.dates);
    return this.duration;
  }

  public onSubmit(): void {
    if (this.createAbsenceForm.valid) {
      let newAbsence = new Absence(
        this.createAbsenceForm.controls["type"].value,
        this.createAbsenceForm.controls["summary"].value,
        Absence.convertDate(this.createAbsenceForm.controls["begin"].value),
        Absence.convertDate(this.createAbsenceForm.controls["end"].value),
        this.createAbsenceForm.controls["duration"].value,
        this.createAbsenceForm.controls["reporter"].value,
        this.createAbsenceForm.controls["assignee"].value
      );
      this.api
        .adminAbsence()
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
