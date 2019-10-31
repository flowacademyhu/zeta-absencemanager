import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { User } from 'src/app/models/User.model';

@Component({
  selector: 'app-employee-profile-edit-modal',
  templateUrl: './employee-profile-edit-modal.component.html',
  styleUrls: ['./employee-profile-edit-modal.component.css']
})
export class EmployeeProfileEditModalComponent implements OnInit {
  public employeeProfileEditForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<EmployeeProfileEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public fb: FormBuilder
  ) {
    this.employeeProfileEditForm = new FormGroup({
      firstName: new FormControl(
        data.user.firstName,
        [
        Validators.required,
        Validators.maxLength(60)
      ]),
      lastName: new FormControl(
        data.user.lastName, 
        [
        Validators.required,
        Validators.maxLength(60)
      ]),
      dateOfBirth: new FormControl( 
        new Date (
          data.user.dateOfBirth[0],
          data.user.dateOfBirth[1] - 1,  
          data.user.dateOfBirth[2]
          )),
      numberOfChildren: new FormControl(
        data.user.numberOfChildren, Validators.required)
    })
   }

  ngOnInit() {
  }

}
