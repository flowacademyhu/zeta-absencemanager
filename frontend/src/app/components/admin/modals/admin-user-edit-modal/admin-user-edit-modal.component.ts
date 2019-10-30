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
import { User } from "src/app/models/User.model";

@Component({
  selector: "app-admin-user-edit-modal",
  templateUrl: "./admin-user-edit-modal.component.html",
  styleUrls: ["./admin-user-edit-modal.component.css"]
})
export class AdminUserEditModalComponent implements OnInit {
  public editUserDataForm: FormGroup;
  private error: string;

  constructor(
    private api: ApiCommunicationService,
    public dialogRef: MatDialogRef<AdminUserEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public fb: FormBuilder
  ) {
    console.log(data.user);
    this.editUserDataForm = new FormGroup({
      firstName: new FormControl(data.user.firstName, Validators.required),
      lastName: new FormControl(data.user.lastName, Validators.required),
      dateOfBirth: new FormControl(data.user.dateOfBirth),
      dateOfEntry: new FormControl(data.user.dateOfEntry),
      endOfTrial: new FormControl(data.user.dateOfEndTrial),
      position: new FormControl(data.user.position, Validators.required),
      group: new FormControl(data.user.group),
      email: new FormControl(data.user.email, Validators.required),
      numberOfChildren: new FormControl(data.user.numberOfChildren),
      otherAbsenceEnt: new FormControl(data.user.otherAbsenceEnt)
    });
  }

  ngOnInit() {}
}
