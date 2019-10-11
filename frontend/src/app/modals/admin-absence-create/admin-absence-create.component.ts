import { Component, OnInit, Inject } from '@angular/core';
// import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Absence } from 'src/app/models/Absence.model';


@Component({
  selector: 'app-admin-absence-create',
  templateUrl: './admin-absence-create.component.html',
  styleUrls: ['./admin-absence-create.component.css']
})
export class AdminAbsenceCreateComponent implements OnInit {

  form: FormGroup;
  description: string;
  newAbsence: Absence;
  type: string;
  summary: string;
  begin: string;
  end: string;
  reporter: string;
  assignee: string;
  status: string;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<AdminAbsenceCreateComponent>,
    @Inject(MAT_DIALOG_DATA) data) {

    this.description = data.description;
    /*     this.newAbsence.type = data.type;
        this.newAbsence.summary = data.summary;
        this.newAbsence.begin = data.begin;
        this.newAbsence.end = data.end;
        this.newAbsence.reporter = data.reporter;
        this.newAbsence.assignee = data.assignee;
        this.newAbsence.status = data.status; */
  }

  ngOnInit() {
    this.form = this.fb.group({
      type: ['', []],
      summary: ['', []],
      begin: ['', []],
      end: ['', []],
      reporter: ['', []],
      assignee: ['', []],
      status: ['', []],
    });
  }

  save(): void {
    this.dialogRef.close(this.form.value);
  }

  close(): void {
    this.dialogRef.close();
  }
}
