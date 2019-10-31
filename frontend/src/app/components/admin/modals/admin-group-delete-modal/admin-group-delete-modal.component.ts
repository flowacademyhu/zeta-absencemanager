import { Component, OnInit, Inject } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material";
import { Group } from "src/app/models/Group.model";
import { FormBuilder } from "@angular/forms";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { User } from "src/app/models/User.model";

@Component({
  selector: "app-admin-group-delete-modal",
  templateUrl: "./admin-group-delete-modal.component.html",
  styleUrls: ["./admin-group-delete-modal.component.css"]
})
export class AdminGroupDeleteModalComponent implements OnInit {
  private employees: User[];

  constructor(
    public dialogRef: MatDialogRef<AdminGroupDeleteModalComponent>,
    private api: ApiCommunicationService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public fb: FormBuilder
  ) {}

  ngOnInit() {
    this.api
      .user()
      .getEverybodyByGroup(this.data.group.id)
      .subscribe(data => {
        this.employees = data;
      });
  }
}
