import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/ApiCommunication.service";

@Component({
  selector: 'app-admin-user-edit',
  templateUrl: './admin-user-edit.component.html',
  styleUrls: ['./admin-user-edit.component.css']
})
export class AdminUserEditComponent implements OnInit {
  private editUserDataForm: FormGroup;
  private error: string;

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<AdminUserEditComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) { }

  ngOnInit() {
    this.editUserDataForm = new FormGroup({
      firstName: new FormControl("", Validators.required),
      lastName: new FormControl(""),
      dateOfBirth: new FormControl("", Validators.required),
      position: new FormControl("", Validators.required),
      supervisor: new FormControl(""),
      endOfTrial: new FormControl("", Validators.required),
      email: new FormControl("", Validators.required)
    });
  }

/*    public OnSubmit(editUserDataFormValue): void {
    if (this.editUserDataForm.valid) {
      let newAbsence = new Absence(
        editUserDataFormValue.type,
        editUserDataFormValue.summary,
        editUserDataFormValue.start,
        editUserDataFormValue.end
      );
      this.api
      .user()
      .updateUser()
    }  */


}