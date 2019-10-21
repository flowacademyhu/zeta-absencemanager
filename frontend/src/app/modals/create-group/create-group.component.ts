import { Component, OnInit, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import {
  FormBuilder,
  FormGroup,
  Validators,
  FormControl
} from "@angular/forms";
import { User } from "src/app/models/User.model";
import { ApiCommunicationService } from "src/app/services/ApiCommunication.service";
import { Group } from 'src/app/models/Group.model';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css']
})
export class CreateGroupComponent implements OnInit {

  public createGroupForm: FormGroup;
  private groupList: Group[];
  private userList: User[] = [];
  private employeeList: User[] = [];
  private leaderList: User[] = [];

  constructor(
    public dialogRef: MatDialogRef<CreateGroupComponent>,
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
    this.dialogRef.updateSize("60%", "90%");
    this.api.group().getGroups().subscribe(groups => {
      this.groupList = groups;
      // this.dataSource = new MatTableDataSource(this.groupsList);
      console.log(this.groupList);
    })

    this.api.user().getUsers().subscribe(users => {
      this.userList = users;
      // this.dataSourceUser = new MatTableDataSource(this.usersList);
      console.log(this.userList);
      for (let i = 0; i < this.userList.length; i++) {
        if (this.userList[i].group === null) {
        this.employeeList.push(this.userList[i])
        }
      }
    })
  }

  addLeadersToList(group) {
    this.leaderList.splice(0, this.leaderList.length);
    for (let i = 0; i < group.employees.length; i++) {
      this.leaderList.push(group.employees[i]);
    }
    console.log(this.leaderList);
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
