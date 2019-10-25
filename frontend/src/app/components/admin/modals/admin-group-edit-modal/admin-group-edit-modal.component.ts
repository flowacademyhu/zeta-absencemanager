import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { User } from 'src/app/models/User.model';

@Component({
  selector: 'app-admin-group-edit-modal',
  templateUrl: './admin-group-edit-modal.component.html',
  styleUrls: ['./admin-group-edit-modal.component.css']
})
export class AdminGroupEditModalComponent implements OnInit {
  public editGroupDataForm: FormGroup;
  private userList: User[];
  private employees: User[] = [];


  constructor(
    private api: ApiCommunicationService,
    public dialogRef: MatDialogRef<AdminGroupEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public fb: FormBuilder
  ) {
    this.editGroupDataForm = new FormGroup({
      name: new FormControl(data.group.name, Validators.required),
      parentId: new FormControl(data.group.parentId, Validators.required),
      leaders: new FormControl(null, Validators.required),
      employees: new FormControl(null, Validators.required)
      });

    }

  ngOnInit() {
    this.api.user().getUsers().subscribe(users => {
      this.userList = users;
      console.log(this.userList);
      this.editGroupDataForm.patchValue( {
        leaders: this.findLeader(),
        employees: this.findEmployees(),
      });
    });
    this.dialogRef.updateSize("25%", "90%");
  }

  private findLeader(){
    return this.userList.find(leader => leader.id === this.data.group.leaders[0].id);
  }

  private findEmployees(){
    this.userList.filter(employee => {
      if(employee.group && (employee.group.id === this.data.group.id)) {
        this.employees.push(employee);
      }
    });
    console.log(this.employees);
  return this.employees
  }

}
