import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from "@angular/material";

@Component({
  selector: 'app-admin-absence-create',
  templateUrl: './admin-absence-create.component.html',
  styleUrls: ['./admin-absence-create.component.css']
})
export class AdminAbsenceCreateComponent implements OnInit {

  constructor(private dialog: MatDialog) { }

  ngOnInit() {
  }


  openDialog() {

    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;

    this.dialog.open(AdminAbsenceCreateComponent, dialogConfig);
  }

}
