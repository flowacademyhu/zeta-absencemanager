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
import { Subject } from 'rxjs';
import { CreateUserComponent } from 'src/app/modals/create-user/create-user.component';
import { takeUntil } from 'rxjs/operators';
import { MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent implements OnInit {
  private createGroupForm: FormGroup;
  // private types;
/*   private groups: any;
  private users: any;

  private leaders = this.users;
  private employees = this.users;

  private parentGroupId: any
  private addedLeaders: any;
  private addedEmployees: any; */

 /*  private error: string; */

 private selectedLeaders: User[] = [];
 private selectedEmployees: User[] = [];

  displayedColumnsGroup: string[] = ['name', 'id', 'leader1', 'leader2'];
  displayedColumnsUser: string[] = ['name', 'id'];
  // dataSource; // --> filter

  groupData: Group;
  groupsList: Group[];

  userData: User;
  usersList: User[];

  employeeList: User[];
  leaderList: User[];

  private unsubscribe$ = new Subject<void>();

  constructor(
    private api: ApiCommunicationService,
    public dialog: MatDialog) {}
    private dialogRef: MatDialogRef<GroupCreateComponent>;
    @Inject(MAT_DIALOG_DATA) private data: any

  /* createUser(): void {
    const dialogRef = this.dialog.open(CreateUserComponent, {
      data: {user: this.userData}
    });

    dialogRef.afterClosed().pipe(takeUntil(this.unsubscribe$)).subscribe(result => {
      this.userData = result;
      this.userData.isOnTrial = true;
      this.userData.role = "EMPLOYEE";
      this.dateConverter();
      console.log(this.userData);      
      this.api.user().createUser(this.userData).subscribe(u => console.log("created:" + u));
    });
    
  } */

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

  ngOnInit() {
/*     this.parentGroupId = this.groupSelector(Group)
    this.addedLeaders = this.userSelector(User);
    this.addedEmployees = this.userSelector(User); */
  
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

    this.createGroupForm = new FormGroup({
      name: new FormControl("", Validators.required),
      parentId: new FormControl(""),
      leaders: new FormControl(""),
      employees: new FormControl("")
    });
  }

  addToSelectedLeaders(leader: User, index: number) {
    console.log("leader: " + leader);
    this.selectedLeaders.push(leader);
    this.leaderList.slice(index, index);
    console.log(this.selectedLeaders)
  }

  addToSelectedEmployees(employee, index) {
    console.log("employee: " + employee);
    this.selectedEmployees.push(employee);
    this.employeeList.slice(index, index);
    console.log(this.selectedEmployees)
  }

  
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete(); 
  }

  /* private dateConverter() {
    this.userData.dateOfEndTrial = (this.userData.dateOfEndTrial as Date).toISOString().split("T")[0].split("-");
    this.userData.dateOfBirth = (this.userData.dateOfBirth as Date).toISOString().split("T")[0].split("-");
    this.userData.dateOfEntry = (this.userData.dateOfEntry as Date).toISOString().split("T")[0].split("-"); 
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfEntry[i] = parseInt(this.userData.dateOfEntry[i]);
    }
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfBirth[i] = parseInt(this.userData.dateOfBirth[i]);
    }
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfEndTrial[i] = parseInt(this.userData.dateOfEndTrial[i]);
    }
  } */

  /* groupSelector(definition) {
    return Object.keys(definition).map(key => ({
      value: key,
      title: definition[key]
    }));
  } */
  

  public OnSubmit(createGroupFormValue): void { 
    if (this.createGroupForm.valid) {
      let newGroup = new Group(
        createGroupFormValue.name,
        createGroupFormValue.parentId,
        createGroupFormValue.leaders,
        createGroupFormValue.employees
      );
      this.api
        .group()
        .createGroup(newGroup)
        .subscribe(
        /*   data => {
            this.dialogRef.close();
          },
          err => {
            this.error = err.error.message;
          } */
        );
        /* if (newGroup.leaders.length > 0) {
          this.selectedLeaders = newGroup.leaders
        } */
    }
  }

  public onCancel(): void {
    this.dialogRef.close();
  }

  /* constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<GroupCreateComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {} */

}