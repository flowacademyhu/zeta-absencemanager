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

  constructor(
    public dialogRef: MatDialogRef<AdminUserDeleteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User,
    public fb: FormBuilder,
  ) { }

  ngOnInit() {  }
}
