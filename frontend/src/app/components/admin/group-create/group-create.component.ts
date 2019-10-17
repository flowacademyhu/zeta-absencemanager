import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Group } from 'src/app/models/Group.model';
import { User } from 'src/app/models/User.model';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/ApiCommunication.service";


@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent implements OnInit {
  private createGroupForm: FormGroup;
  // private types;
  private groups: any;
  private users: any;

  private leaders = this.users;
  private employees = this.users;

  private parentGroupId: any
  private addedLeaders: any;
  private addedEmployees: any;

  private error: string;

  groupSelector(definition) {
    return Object.keys(definition).map(key => ({
      value: key,
      title: definition[key]
    }));
  }

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<GroupCreateComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.parentGroupId = this.groupSelector(Group)
    this.addedLeaders = this.groupSelector(User);
    this.addedEmployees = this.groupSelector(User);
    
    this.api.group().getGroups().subscribe(k => this.groups = k);
    this.api.user().getUsers().subscribe(k => this.users = k)
    this.createGroupForm = new FormGroup({
      name: new FormControl("", Validators.required),
      parentId: new FormControl(""),
      leaders: new FormControl(""),
      employees: new FormControl("")
    });
  }

  // Lehet, h nem jó!
  private async getGroups() {
    this.api.group().getGroups().subscribe();
  }

  private async getUsers() {
    this.api.user().getUsers().subscribe();
  }

  public OnSubmit(createGroupFormValue): void { 
    if (this.createGroupForm.valid) {
      let newGroup = new Group(
        createGroupFormValue.type,
        createGroupFormValue.parentId,
        createGroupFormValue.leaders,
        createGroupFormValue.employees
      );
      this.api
        .group()
        .createGroup(newGroup)
        .subscribe(
          data => {
            this.dialogRef.close();
          },
          err => {
            this.error = err.error.message;
          }
        );
    }
  }

  public onCancel(): void {
    this.dialogRef.close();
  }
}