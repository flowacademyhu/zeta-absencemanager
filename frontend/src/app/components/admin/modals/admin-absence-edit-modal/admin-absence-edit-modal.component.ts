import { Component, OnInit, Inject, OnDestroy } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import { Subject } from "rxjs";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { takeUntil } from "rxjs/operators";
import { User } from "src/app/models/User.model";
import * as moment from "moment";

@Component({
  selector: "app-admin-absence-edit-modal",
  templateUrl: "./admin-absence-edit-modal.component.html",
  styleUrls: ["./admin-absence-edit-modal.component.css"]
})
export class AdminAbsenceEditModalComponent implements OnInit, OnDestroy {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;
  private absence: Absence;
  private message = "Edit";
  private _unsubscribe$ = new Subject<void>();
  private leaders: User[];
  private users: User[];
  private isTypeEdit = { value: false };
  private isSummaryEdit = { value: false };
  private isBeginEdit = { value: false };
  private isEndEdit = { value: false };
  private isDurationEdit = { value: false };
  private isReporterEdit = { value: false };
  private isAssigneeEdit = { value: false };
  private duration = 0;
  private dates = [false, false];

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<AdminAbsenceEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.createAbsenceForm = new FormGroup({
      type: new FormControl("", Validators.required),
      summary: new FormControl(""),
      begin: new FormControl("", Validators.required),
      end: new FormControl("", Validators.required),
      duration: new FormControl(""),
      reporter: new FormControl("", Validators.required),
      assignee: new FormControl("", Validators.required)
    });
    this.api
      .user()
      .getLeaders()
      .subscribe(data => {
        this.leaders = data;
        this.api
          .user()
          .getUsers()
          .subscribe(data => {
            this.users = data;
            this.api
              .adminAbsence()
              .getAbsence(this.data.absence.id)
              .subscribe(
                data => {
                  this.absence = data;
                  this.absence.begin = new Date(
                    data.begin[0],
                    data.begin[1] - 1,
                    data.begin[2]
                  );
                  this.absence.end = new Date(
                    data.end[0],
                    data.end[1] - 1,
                    data.end[2]
                  );
                  this.types = Absence.enumSelector(AbsenceType);
                  this.createAbsenceForm.patchValue({
                    type: this.absence.type,
                    summary: this.absence.summary,
                    begin: this.absence.begin,
                    end: this.absence.end,
                    duration: this.absence.duration,
                    reporter: this.users.find(
                      e => e.id === this.absence.reporter.id
                    ),
                    assignee: this.leaders.find(
                      e => e.id === this.absence.assignee.id
                    )
                  });
                },
                err => {
                  this.error = err.error.message;
                }
              );
          });
      });
  }

  public onSubmit(): void {
    if (
      this.isTypeEdit.value ||
      this.isSummaryEdit.value ||
      this.isBeginEdit.value ||
      this.isEndEdit.value ||
      this.isReporterEdit.value ||
      this.isAssigneeEdit.value ||
      this.isDurationEdit.value
    ) {
      this.isTypeEdit.value = false;
      this.isSummaryEdit.value = false;
      this.isBeginEdit.value = false;
      this.isEndEdit.value = false;
      this.isReporterEdit.value = false;
      this.isAssigneeEdit.value = false;
      this.isDurationEdit.value = false;

      this.message = "Edit";
      if (this.createAbsenceForm.valid) {
        this.absence.type = this.createAbsenceForm.controls["type"].value;
        this.absence.summary = this.createAbsenceForm.controls["summary"].value;
        this.absence.begin = Absence.convertDate(
          this.createAbsenceForm.controls["begin"].value
        );
        this.absence.end = Absence.convertDate(
          this.createAbsenceForm.controls["end"].value
        );
        this.absence.reporter = this.createAbsenceForm.controls[
          "reporter"
        ].value;
        this.absence.assignee = this.createAbsenceForm.controls[
          "assignee"
        ].value;
        this.absence.duration = this.createAbsenceForm.controls[
          "duration"
        ].value;
        /*         if (this.absence.duration === 0 || this.absence.duration == null) {
          var begin = moment(this.absence.begin);
          var end = moment(this.absence.end);
          var duration =
            Math.floor(moment.duration(end.diff(begin)).asDays()) + 1;
          this.absence.duration = duration;
        } */
        this.api
          .adminAbsence()
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
      this.isTypeEdit.value = true;
      this.isSummaryEdit.value = true;
      this.isBeginEdit.value = true;
      this.isEndEdit.value = true;
      this.isReporterEdit.value = true;
      this.isAssigneeEdit.value = true;
      this.isDurationEdit.value = true;
      this.message = "Save";
    }
  }

  onEdit(element) {
    element.value = true;
    this.message = "Save";
  }

  public countDuration(): number {
    this.duration = 0;
    var begin = moment(this.createAbsenceForm.controls["begin"].value);
    var end = moment(this.createAbsenceForm.controls["end"].value);
    this.duration = Math.floor(moment.duration(end.diff(begin)).asDays()) + 1;
    this.createAbsenceForm.controls["duration"].setValue(this.duration);
    return this.duration;
  }

  changeHandler(event): number {
    if (event.targetElement.id === "begin") {
      this.dates[0] = true;
    } else {
      this.dates[1] = true;
    }
    if (this.dates[0] === true && this.dates[1] === true) {
      this.countDuration();
    }
    return this.duration;
  }

  public onCancel(): void {
    this.dialogRef.close();
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }
}
