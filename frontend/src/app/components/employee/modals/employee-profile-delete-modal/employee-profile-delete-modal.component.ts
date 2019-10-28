import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { AdminUserDeleteModalComponent } from 'src/app/components/admin/modals/admin-user-delete-modal/admin-user-delete-modal.component';
import { User } from 'src/app/models/User.model';

@Component({
  selector: 'app-employee-profile-delete-modal',
  templateUrl: './employee-profile-delete-modal.component.html',
  styleUrls: ['./employee-profile-delete-modal.component.css']
})
export class EmployeeProfileDeleteModalComponent implements OnInit {

  public deleteUserForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<EmployeeProfileDeleteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User,
    public fb: FormBuilder,
  ) { }

  ngOnInit() {  }

}
