import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { User } from 'src/app/models/User.model';
import { Group } from 'src/app/models/Group.model';

@Component({
  selector: 'app-admin-group-edit-modal',
  templateUrl: './admin-group-edit-modal.component.html',
  styleUrls: ['./admin-group-edit-modal.component.css']
})
export class AdminGroupEditModalComponent implements OnInit {
  public editGroupDataForm: FormGroup;
  private leader: User;
  private userList: User[];
  private groupList: Group[];
  private employeeListByGroupIsNull: User[] = [];
  private employeeListByOrigParentGroup: User[] = [];
  private employeeList: User[] =  [];


  constructor(
    private api: ApiCommunicationService,
    public dialogRef: MatDialogRef<AdminGroupEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public fb: FormBuilder
  ) {
    this.editGroupDataForm = new FormGroup({
      name: new FormControl(data.group.name,
        Validators.required),
      parent: new FormControl(data.group.parentId),
      leader: new FormControl(this.data.group.leader, Validators.required)
      });
    }

  private editedGroup : Group = this.data.group;  

  ngOnInit() {
    this.dialogRef.updateSize("35%", "60%");
    console.log(this.data.group)
    this.api
    .user()
    .getEmployees()
    .subscribe( e => {
      this.employeeListByGroupIsNull = e;
    })
    if (this.data.group.parentId) {
    this.api
    .user()
    .getEmployeesByGroup(this.data.group.parentId)
    .subscribe(e => {
      this.employeeList = e;
      this.employeeList.push(this.data.group.leader);
      })
    }
    if (!this.data.group.parentId) {
      this.api
      .user()
      .getEmployees()
      .subscribe( e => {
        this.employeeList = e;
        this.employeeList.push(this.data.group.leader);
        console.log(this.employeeList);
      })
    }
    this.api
    .group()
    .getGroups()
    .subscribe(groups => { 
      this.groupList = groups;
      for (let i = 0; i < this.groupList.length; i++) {
        if (this.groupList[i].id == this.data.group.id) {
          this.groupList.splice(i, 1);
        }
      }
    });
    
  }

  
  addEmployeesToList(id: number) {
    if (id > 0) {
    this.api
      .user()
      .getEmployeesByGroup(id)
      .subscribe((e: User[]) => {
        this.employeeList = e;
        if (id === this.data.group.parentId) {
          this.employeeList.push(this.data.group.leader);
          this.employeeList.forEach(element => {
          });
        }
      });
    }
    if (id === 0) {
      this.employeeList = this.employeeListByGroupIsNull;
      if (!this.data.group.parentId) {
        this.employeeList.push(this.data.group.leader);
      }
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
  

  onCancel() {
    this.dialogRef.close();
  }
}
