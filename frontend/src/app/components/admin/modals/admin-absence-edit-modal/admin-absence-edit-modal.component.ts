import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import { Subject } from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { takeUntil } from "rxjs/operators";
import { User } from "src/app/models/User.model";

@Component({
  selector: "app-admin-absence-edit-modal",
  templateUrl: "./admin-absence-edit-modal.component.html",
  styleUrls: ["./admin-absence-edit-modal.component.css"]
})
export class AdminAbsenceEditModalComponent implements OnInit {
  private createAbsenceForm: FormGroup = new FormGroup({});
  private types;
  private error: string;
  private absence: Absence;
  private update: boolean = false;
  private message = "Edit";
  private _unsubscribe$ = new Subject<void>();
  private leaders: User[];
  private users: User[];

  constructor(
    private route: ActivatedRoute,
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<AdminAbsenceEditModalComponent>,
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
    this.api
      .adminAbsence()
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
          console.log(this.absence.reporter);
          this.createAbsenceForm = new FormGroup({
            type: new FormControl(this.absence.type),
            summary: new FormControl(this.absence.summary),
            start: new FormControl(this.absence.begin),
            end: new FormControl(this.absence.end),
            reporter: new FormControl(this.absence.reporter), //TODO doesn't appear in the input field
            assignee: new FormControl(this.absence.assignee)
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
      this.message = "Edit";
      if (this.createAbsenceForm.valid) {
        this.absence.type = createAbsenceFormValue.type;
        this.absence.summary = createAbsenceFormValue.summary;
        this.absence.begin = createAbsenceFormValue.start;
        this.absence.end = createAbsenceFormValue.end;
        this.absence.reporter = createAbsenceFormValue.reporter;
        this.absence.assignee = createAbsenceFormValue.assignee;
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
