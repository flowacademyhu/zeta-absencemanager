import { Component, OnInit, Inject, OnDestroy } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType, Status } from "src/app/models/Absence.model";
import { Subject, Observable, forkJoin } from "rxjs";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { takeUntil } from "rxjs/operators";
import { User } from "src/app/models/User.model";
import * as moment from "moment";
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: "app-admin-absence-edit-modal",
  templateUrl: "./admin-absence-edit-modal.component.html",
  styleUrls: ["./admin-absence-edit-modal.component.css"]
})
export class AdminAbsenceEditModalComponent implements OnInit, OnDestroy {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;
  private userRole;
  private absence: Absence;
  private _unsubscribe$ = new Subject<void>();
  private leaders: User[];
  private users: User[];
  private formEditState = {
    isTypeEdited: false,
    isSummaryEdited: false,
    isBeginEdited: false,
    isEndEdited: false,
    isDurationEdited: false,
    isReporterEdited: false,
    isAssigneeEdited: false
  };
  private duration = 0;
  private dates = [false, false];

  constructor(
    private api: ApiCommunicationService,
    private session: SessionService,
    private dialogRef: MatDialogRef<AdminAbsenceEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.currentUser();
    this.createAbsenceForm = new FormGroup({
      type: new FormControl("", Validators.required),
      summary: new FormControl(""),
      begin: new FormControl("", Validators.required),
      end: new FormControl("", Validators.required),
      duration: new FormControl(""),
      reporter: new FormControl("", Validators.required),
      assignee: new FormControl("", Validators.required)
    });

    this.requestDatas().subscribe(
      responseList => {
        this.leaders = responseList[0];
        this.users = responseList[1];
        this.absence = responseList[2];
        this.absence.begin = new Date(
          responseList[2].begin[0],
          responseList[2].begin[1] - 1,
          responseList[2].begin[2]
        );
        this.absence.end = new Date(
          responseList[2].end[0],
          responseList[2].end[1] - 1,
          responseList[2].end[2]
        );
        this.types = Absence.enumSelector(AbsenceType);
        this.createAbsenceForm.patchValue({
          type: this.absence.type,
          summary: this.absence.summary,
          begin: this.absence.begin,
          end: this.absence.end,
          duration: this.absence.duration,
          reporter: this.users.find(e => e.id === this.absence.reporter.id),
          assignee: this.leaders.find(e => e.id === this.absence.assignee.id)
        });
      },
      err => {
        this.error = err.error.message;
      }
    );
  }

  public requestDatas(): Observable<any[]> {
    let leaders = this.api.user().getLeaders();
    let users = this.api.user().getUsers();
    let absence = this.api.adminAbsence().getAbsence(this.data.absence.id);
    return forkJoin([leaders, users, absence]);
  }

  public onSubmit(): void {
    if (this.isFormEdited()) {
      this.setFormEdited(false);
      if (this.createAbsenceForm.valid) {
        this.getFormData();
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
      this.setFormEdited(true);
    }
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
    this.countDuration();
    return this.duration;
  }

  public isFormEdited(): boolean {
    return (
      this.formEditState.isTypeEdited ||
      this.formEditState.isSummaryEdited ||
      this.formEditState.isBeginEdited ||
      this.formEditState.isEndEdited ||
      this.formEditState.isDurationEdited ||
      this.formEditState.isReporterEdited ||
      this.formEditState.isAssigneeEdited
    );
  }

  public setFormEdited(value: boolean) {
    this.formEditState.isTypeEdited = value;
    this.formEditState.isSummaryEdited = value;
    this.formEditState.isBeginEdited = value;
    this.formEditState.isEndEdited = value;
    this.formEditState.isDurationEdited = value;
    this.formEditState.isReporterEdited = value;
    this.formEditState.isAssigneeEdited = value;
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
    this.absence.reporter = this.createAbsenceForm.controls["reporter"].value;
    this.absence.assignee = this.createAbsenceForm.controls["assignee"].value;
    this.absence.duration = this.createAbsenceForm.controls["duration"].value;
  }

  public onCancel(): void {
    this.dialogRef.close();
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  public currentUser() {
    var user = this.session.getUserData();
    this.userRole = user.role;
  }

  public onApprove() {
    this.absence.status = Status.APPROVED;
    var ab = {id: this.absence.id,begin: this.absence.begin, end: this.absence.end, assignee: this.absence.assignee, type: this.absence.type,
      duration: this.absence.duration, reporter: this.absence.reporter, status: "APPROVED"};
    this.api.absence().updateAbsence(this.absence.id, ab).subscribe(result => console.log(result));
  }

  public onReject() {
    this.absence.status = Status.APPROVED;
    var ab = {id: this.absence.id,begin: this.absence.begin, end: this.absence.end, assignee: this.absence.assignee, type: this.absence.type,
      duration: this.absence.duration, reporter: this.absence.reporter, status: "REJECTED"};
    this.api.adminAbsence().updateAbsence(this.absence.id, ab).subscribe(result => console.log(result));
  }

  public onAdministrate() {
    this.absence.status = Status.ADMINISTRATED;
    var ab = {id: this.absence.id,begin: this.absence.begin, end: this.absence.end, assignee: this.absence.assignee, type: this.absence.type,
      duration: this.absence.duration, reporter: this.absence.reporter, status: "ADMINISTRATED"};
    this.api.adminAbsence().updateAbsence(this.absence.id, ab).subscribe(result => console.log(result));
  }
}
