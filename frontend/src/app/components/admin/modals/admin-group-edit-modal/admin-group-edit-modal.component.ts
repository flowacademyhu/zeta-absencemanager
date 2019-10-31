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


  constructor(
    private api: ApiCommunicationService,
    public dialogRef: MatDialogRef<AdminGroupEditModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public fb: FormBuilder
  ) {
    this.editGroupDataForm = new FormGroup({
      name: new FormControl(data.group.name, Validators.required),
      parent: new FormControl(data.group.parentId, Validators.required),
      leader: new FormControl(this.data.group.leader.id, Validators.required)
      });
    }

  ngOnInit() {
    this.api.user().getUsers().subscribe(users => this.userList = users);
    this.api.group().getGroups().subscribe(groups => this.groupList = groups);
    this.dialogRef.updateSize("35%", "60%");
  }

  onCancel() {
    this.dialogRef.close();
  }
}
