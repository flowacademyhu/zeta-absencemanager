import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { User } from 'src/app/models/User.model';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';

@Component({
  selector: 'app-admin-user-delete-modal',
  templateUrl: './admin-user-delete-modal.component.html',
  styleUrls: ['./admin-user-delete-modal.component.css']
})
export class AdminUserDeleteModalComponent implements OnInit {
  public deleteUserForm: FormGroup;
  private userList: User[] = [];

  constructor(
    public dialogRef: MatDialogRef<AdminUserDeleteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User,
    public fb: FormBuilder,
    private api: ApiCommunicationService
  ) {
    this.deleteUserForm = new FormGroup({
      name: new FormControl("", [
        Validators.required,
        Validators.maxLength(60)
      ]),
      parentId: new FormControl(null),
      leader: new FormControl(null, Validators.required),
      employees: new FormControl(null)
    });
  }

  ngOnInit() {
  }


  /* groupSelector(definition) {
    return Object.keys(definition).map(key => ({
      value: key,
      title: definition[key]
    }));
  }

  userSelector(definition) {
    return Object.keys(definition).map(key => ({
      value: key,
      title: definition[key]
    }));
  } */
}
