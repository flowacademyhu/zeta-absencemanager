import { Component, OnInit, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl
} from "@angular/forms";
import { User } from "src/app/models/User.model";
import { Group } from "src/app/models/Group.model";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { RouterLink } from "@angular/router";

@Component({
  selector: "app-admin-group-create-modal",
  templateUrl: "./admin-group-create-modal.component.html",
  styleUrls: ["./admin-group-create-modal.component.css"]
})
export class AdminGroupCreateModalComponent implements OnInit {
  public createGroupForm: FormGroup;
  private employeeListByGroupIsNull: User[] = [];
  private employeeList: User[] = [];

  constructor(
    public dialogRef: MatDialogRef<AdminGroupCreateModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Group[],
    public fb: FormBuilder,
    private api: ApiCommunicationService
  ) {
    this.createGroupForm = new FormGroup({
      name: new FormControl("", [
        Validators.required,
        Validators.maxLength(60)
      ]),
      parentId: new FormControl(null),
      leaderId: new FormControl(null, [Validators.required]),
      minimalWorkers: new FormControl(null, [Validators.required])
    });
  }

  ngOnInit() {
    this.dialogRef.updateSize("35%", "50%");
    this.api
      .user()
      .getEmployees()
      .subscribe(e => {
        this.employeeListByGroupIsNull = e;
        for (let i = 0; i < this.employeeListByGroupIsNull.length; i++) {
          this.employeeList.push(this.employeeListByGroupIsNull[i]);
        }
      });
  }

  addEmployeesToList(id: number) {
    if (id > 0) {
      this.api
        .user()
        .getEmployeesByGroup(id)
        .subscribe(e => {
          this.employeeList = e;
          console.log(this.employeeList);
        });
    }
    if (id === 0) {
      this.employeeList = this.employeeListByGroupIsNull;
    }
  }

  groupSelector(definition) {
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
  }

  public onCancel(): void {
    this.dialogRef.close();
  }
}
