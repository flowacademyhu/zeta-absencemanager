import { Component, OnInit, Inject, OnDestroy } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import { Subject } from "rxjs";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { takeUntil } from "rxjs/operators";
import { User } from "src/app/models/User.model";

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
  private update: boolean = false;
  private message = "Edit";
  private _unsubscribe$ = new Subject<void>();
  private leaders: User[];
  private users: User[];

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
      reporter: new FormControl("", Validators.required),
      assignee: new FormControl("", Validators.required)
    });
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
            reporter: this.absence.reporter,
            assignee: this.absence.assignee
          });
        },
        err => {
          this.error = err.error.message;
        }
      );
  }

  public onSubmit(): void {
    if (this.update) {
      this.update = !this.update;
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
