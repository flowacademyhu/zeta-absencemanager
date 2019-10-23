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
      assignee: new FormControl("", Validators.required)
    });
  }

  public onSubmit(createAbsenceFormValue): void {
    if (this.createAbsenceForm.valid) {
      let newAbsence = new Absence(
        createAbsenceFormValue.type,
        createAbsenceFormValue.summary,
        Absence.convertDate(createAbsenceFormValue.begin),
        Absence.convertDate(createAbsenceFormValue.end),
        createAbsenceFormValue.reporter,
        createAbsenceFormValue.assignee
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
