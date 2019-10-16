import { Component, OnInit } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { AbsencesIndexComponent } from "../../admin/absences-index/absences-index.component";
import { AbsencesCreateComponent } from "../absences-create/absences-create.component";
const MOCK_DATA: any[] = [
  {
    id: 1,
    begin: 2019 - 10 - 15,
    end: 2019 - 10 - 18,
    days: "4",
    type: "absence",
    status: "open",
    created_at: 1990 - 10 - 10,
    summary: "Summary"
  }
];
@Component({
  selector: "app-emp-absences-index",
  templateUrl: "./emp-absences-index.component.html",
  styleUrls: ["./emp-absences-index.component.css"]
})
export class EmpAbsencesIndexComponent implements OnInit {
  displayedColumns: string[] = [
    "id",
    "created_at",
    "type",
    "summary",
    "begin",
    "end",
    "days",
    "status"
  ];
  dataSource = MOCK_DATA;

  constructor(public dialog: MatDialog) {}

  ngOnInit() {}

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40vw";

    dialogConfig.data = {};

    const dialogRef = this.dialog.open(AbsencesCreateComponent, dialogConfig);

    dialogRef
      .afterClosed()
      .subscribe(data => console.log("Dialog output:", data));
  }
}
