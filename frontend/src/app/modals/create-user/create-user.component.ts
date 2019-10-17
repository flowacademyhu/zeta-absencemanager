import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { User } from 'src/app/models/User.model';
import { Group } from 'src/app/models/Group.model';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';


@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
  public createUserForm: FormGroup;

  groups: Group[];

  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: User, public fb: FormBuilder, private api : ApiCommunicationService) {
      this.createUserForm = new FormGroup({
        firstName: new FormControl('', [Validators.required, Validators.maxLength(60)]),
        lastName: new FormControl('', [Validators.required, Validators.maxLength(60)]),
        dateOfBirth: new FormControl('', [Validators.required]),
        dateOfEntry: new FormControl('', [Validators.required]),
        dateOfEndTrial: new FormControl('', [Validators.required]),
        position: new FormControl('', [Validators.required, Validators.maxLength(60)]),
        email: new FormControl('' , [Validators.required, Validators.email]),
        numberOfChildren: new FormControl('', [Validators.required]),
        otherAbsenceEnt: new FormControl(''),
        group: new FormControl('', [Validators.required])
     });

    
    }

  ngOnInit() {
    /* this.api.group().getGroups().subscribe(g => {
      this.groups = g;
      console.log(g);
    }) */
    this.dialogRef.updateSize('25%', '90%');
  }


}
