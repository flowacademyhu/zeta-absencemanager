import { Component, OnInit, Inject } from "@angular/core";
import {
  FormGroup,
  FormControl,
  Validators,
  FormBuilder
} from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { Group } from "src/app/models/Group.model";

@Component({
  selector: "app-admin-user-edit-modal",
  templateUrl: "./admin-user-edit-modal.component.html",
  styleUrls: ["./admin-user-edit-modal.component.css"]
})
export class AdminUserEditModalComponent implements OnInit {
  public editUserDataForm: FormGroup;
  private error: string;
  private groups: Group[];
  private isOtherAbsenceEnt: boolean = false;

  constructor(
    private api: ApiCommunicationService,
    public dialogRef: MatDialogRef<AdminUserEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public fb: FormBuilder
  ) {
    this.editUserDataForm = new FormGroup({
      firstName: new FormControl("", Validators.required),
      lastName: new FormControl("", Validators.required),
      dateOfBirth: new FormControl(""),
      dateOfEntry: new FormControl(""),
      dateOfEndTrial: new FormControl(""),
      position: new FormControl("", Validators.required),
      group: new FormControl(""),
      email: new FormControl("", Validators.required),
      numberOfChildren: new FormControl(""),
      otherAbsenceEnt: new FormControl(""),
      extraAbsenceDays: new FormControl("")
    });
  }

  ngOnInit() {
    this.api
      .group()
      .getGroups()
      .subscribe(g => {
        this.groups = g;

        this.editUserDataForm.patchValue({
          firstName: this.data.user.firstName,
          lastName: this.data.user.lastName,
          dateOfBirth: new Date(
            this.data.user.dateOfBirth[0],
            this.data.user.dateOfBirth[1] - 1,
            this.data.user.dateOfBirth[2]
          ),
          dateOfEntry: new Date(
            this.data.user.dateOfEntry[0],
            this.data.user.dateOfEntry[1] - 1,
            this.data.user.dateOfEntry[2]
          ),
          dateOfEndTrial: new Date(
            this.data.user.dateOfEndTrial[0],
            this.data.user.dateOfEndTrial[1] - 1,
            this.data.user.dateOfEndTrial[2]
          ),
          position: this.data.user.position,
          group: this.groups.find(g => g.id == this.data.user.group.id),
          email: this.data.user.email,
          numberOfChildren: this.data.user.numberOfChildren,
          otherAbsenceEnt: this.data.user.otherAbsenceEnt,
          extraAbsenceDays: this.data.user.extraAbsenceDays
        });
      });
  }
}
