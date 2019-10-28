import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { User } from 'src/app/models/User.model';
import { Group } from 'src/app/models/Group.model';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-admin-group-delete-modal',
  templateUrl: './admin-group-delete-modal.component.html',
  styleUrls: ['./admin-group-delete-modal.component.css']
})
export class AdminGroupDeleteModalComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<AdminGroupDeleteModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Group,
    public fb: FormBuilder,
  ) { }

  ngOnInit() {  }

}
