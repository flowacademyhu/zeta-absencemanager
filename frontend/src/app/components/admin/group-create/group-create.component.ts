import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Group } from 'src/app/models/Group.model';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/ApiCommunication.service";
import { GroupApiConnector } from 'src/app/models/connectors/GroupApiConnector';
import { UserApiConnector } from 'src/app/models/connectors/UserApiConnector';


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
  private error: string;

  enumSelector(definition) {
    return Object.keys(definition).map(key => ({
      value: key,
      title: definition[key]
    }));
  }

  constructor(
    private api: ApiCommunicationService,
    private groupApi: GroupApiConnector,
    private userApi: UserApiConnector,
    private dialogRef: MatDialogRef<GroupCreateComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    // this.types = this.enumSelector(AbsenceType);
    this.groupApi.getGroups().subscribe(k => this.groups = k);
    this.userApi.getUsers().subscribe(k => this.users = k)
    this.createGroupForm = new FormGroup({
      name: new FormControl("", Validators.required),
      parentId: new FormControl(""),
      leaders: new FormControl(""),
      employees: new FormControl("")
    });
  }

  // Lehet, h nem jó!
  private async getGroups() {
    this.groupApi.getGroups().subscribe();
  }

  private async getUsers() {
    this.userApi.getUsers().subscribe();
  }

  public OnSubmit(createGroupFormValue): void {
    if (this.createGroupForm.valid) {
      let newGroup = new Group(
        createGroupFormValue.type,
        createGroupFormValue.summary,
        createGroupFormValue.start,
        createGroupFormValue.end
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