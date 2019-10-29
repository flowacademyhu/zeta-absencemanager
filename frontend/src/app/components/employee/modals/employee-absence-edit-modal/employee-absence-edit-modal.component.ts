import { Component, OnInit, Inject, OnDestroy } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType, Status } from "src/app/models/Absence.model";
import { Subject } from "rxjs";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { takeUntil } from "rxjs/operators";
import { User } from "src/app/models/User.model";
import * as moment from "moment";

@Component({
  selector: "app-employee-absence-edit-modal",
  templateUrl: "./employee-absence-edit-modal.component.html",
  styleUrls: ["./employee-absence-edit-modal.component.css"]
})
export class EmployeeAbsenceEditModalComponent implements OnInit, OnDestroy {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;
  private absence: Absence;
  private _unsubscribe$ = new Subject<void>();
  private leaders: User[];
  private users: User[];
  private formEditState = {
    isTypeEdited: false,
    isSummaryEdited: false,
    isBeginEdited: false,
    isEndEdited: false,
    isDurationEdited: false
  };
  private duration;

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<EmployeeAbsenceEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.createAbsenceForm = new FormGroup({
      type: new FormControl("", Validators.required),
      summary: new FormControl(""),
      begin: new FormControl("", Validators.required),
      end: new FormControl("", Validators.required),
      duration: new FormControl("", Validators.required)
    });
    this.api
      .absence()
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
            duration: this.absence.duration
          });
        },
        err => {
          this.error = err.error.message;
        }
      );
  }

  public onSubmit(): void {
    if (this.isFormEdited()) {
      this.setFormEdited(false);
      if (this.createAbsenceForm.valid) {
        this.getFormData();
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
      this.setFormEdited(true);
    }
  }

  public isFormEdited(): boolean {
    return (
      this.formEditState.isTypeEdited ||
      this.formEditState.isSummaryEdited ||
      this.formEditState.isBeginEdited ||
      this.formEditState.isEndEdited ||
      this.formEditState.isDurationEdited
    );
  }

  public setFormEdited(value: boolean) {
    this.formEditState.isTypeEdited = value;
    this.formEditState.isSummaryEdited = value;
    this.formEditState.isBeginEdited = value;
    this.formEditState.isEndEdited = value;
    this.formEditState.isDurationEdited = value;
  }

  public getFormData() {
    this.absence.type = this.createAbsenceForm.controls["type"].value;
    this.absence.summary = this.createAbsenceForm.controls["summary"].value;
    this.absence.begin = Absence.convertDate(
      this.createAbsenceForm.controls["begin"].value
    );
    this.absence.end = Absence.convertDate(
      this.createAbsenceForm.controls["end"].value
    );
    this.absence.duration = this.createAbsenceForm.controls["duration"].value;
  }

  public countDuration(): number {
    this.duration = 0;
    var begin = moment(this.createAbsenceForm.controls["begin"].value);
    var end = moment(this.createAbsenceForm.controls["end"].value);
    this.duration = Math.floor(moment.duration(end.diff(begin)).asDays()) + 1;
    this.createAbsenceForm.controls["duration"].setValue(this.duration);
    return this.duration;
  }

  changeHandler(event) {
    this.countDuration();
  }

  public onCancel(): void {
    this.dialogRef.close();
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  public onAction(action: Status): void {
    this.absence.status = action;
    var modifiedAbsence = {
      id: this.absence.id, begin: this.absence.begin, end: this.absence.end, assignee: this.absence.assignee, type: this.absence.type,
      duration: this.absence.duration, reporter: this.absence.reporter, status: action
    };
    this.api.absence().updateAbsence(this.absence.id, modifiedAbsence).subscribe(result => console.log(result));
  }
}
