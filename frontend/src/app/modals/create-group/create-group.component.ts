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
  private selectedLeaders: User[] = [];
  private selectedEmployees: User[] = [];
  // groupData: Group;
  groupsList: Group[];
  employeeList: User[] = [];
  leaderList: User[] = [];

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
      this.groupsList = groups;
      // this.dataSource = new MatTableDataSource(this.groupsList);
      console.log(this.groupsList);
    })

    this.api.user().getUsers().subscribe(users => {
      this.employeeList = users;
      this.leaderList = users;
      // this.dataSourceUser = new MatTableDataSource(this.usersList);
      console.log(this.employeeList);
      console.log(this.leaderList);
    })
  }

  addToSelectedLeaders(leader, index) {
    console.log("leader: " + leader);
    this.selectedLeaders.push(leader);
    this.leaderList.slice(index);
    console.log(this.selectedLeaders)
  }

  addToSelectedEmployees(employee, index) {
    console.log("employee: " + employee);
    this.selectedEmployees.push(employee);
    this.employeeList.slice(index);
    console.log(this.selectedEmployees)
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
