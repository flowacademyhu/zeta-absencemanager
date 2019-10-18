import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators, FormBuilder } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/ApiCommunication.service";
import { User } from 'src/app/models/User.model';

@Component({
  selector: 'app-admin-user-edit',
  templateUrl: './admin-user-edit.component.html',
  styleUrls: ['./admin-user-edit.component.css']
})
export class AdminUserEditComponent implements OnInit {
  public editUserDataForm: FormGroup;
  private error: string;

  constructor(
    private api: ApiCommunicationService,
    public dialogRef: MatDialogRef<AdminUserEditComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public fb: FormBuilder
  ) {
    console.log(data.user);
    this.editUserDataForm = new FormGroup({
      firstName: new FormControl(data.user.firstName, Validators.required),
      lastName: new FormControl(data.user.lastName, Validators.required),
      dateOfBirth: new FormControl(data.user.dateOfBirth),
      position: new FormControl(data.user.position, Validators.required),
      endOfTrial: new FormControl(data.user.dateOfEndTrial),
      email: new FormControl(data.user.email, Validators.required)
    });
   }

  ngOnInit() {
    
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