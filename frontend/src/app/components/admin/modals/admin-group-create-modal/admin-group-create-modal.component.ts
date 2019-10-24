import { Component, OnInit, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl
} from "@angular/forms";
import { User } from "src/app/models/User.model";
import { Group } from 'src/app/models/Group.model';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';

@Component({
  selector: 'app-admin-group-create-modal',
  templateUrl: './admin-group-create-modal.component.html',
  styleUrls: ['./admin-group-create-modal.component.css']
})
export class AdminGroupCreateModalComponent implements OnInit {

  public createGroupForm: FormGroup;
  private groupList: Group[];
  private userList: User[] = [];
  private leaderList: User[] = [];

  constructor(
    public dialogRef: MatDialogRef<AdminGroupCreateModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User,
    public fb: FormBuilder,
    private api: ApiCommunicationService
  ) {
    this.createGroupForm = new FormGroup({
      name: new FormControl("", [
        Validators.required,
        Validators.maxLength(60)
      ]),
      parentId: new FormControl(null),
      leaders: new FormControl(null),
      employees: new FormControl(null)
    });
  }

  ngOnInit() {
    this.dialogRef.updateSize("45%", "90%");
    this.api.group().getGroups().subscribe(groups => {
      this.groupList = groups;
    })
  }

  addLeadersToList(group) {
    this.leaderList.splice(0, this.leaderList.length);
    for (let i = 0; i < group.employees.length; i++) {
      this.leaderList.push(group.employees[i]);
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
}
