import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { userData } from 'src/app/components/admin/user-index/admin-user-index/admin-user-show.component';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';


@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {
  public createUserForm: FormGroup;

  groups: any[] = [
    { name: 'Csop-0' },
    { name: 'Teszt-1' },
    { name: 'Joez-2' }
  ];

  constructor(
    public dialogRef: MatDialogRef<CreateUserComponent>,
    @Inject(MAT_DIALOG_DATA) public data: userData, public fb: FormBuilder) {
      this.createUserForm = new FormGroup({
        firstName: new FormControl('', [Validators.required, Validators.maxLength(60)]),
        lastName: new FormControl('', [Validators.required, Validators.maxLength(60)]),
        dateOfBirth: new FormControl('', [Validators.required]),
        dateOfEntry: new FormControl('', [Validators.required]),
        position: new FormControl('', [Validators.required, Validators.maxLength(60)]),
        email: new FormControl('' , [Validators.required, Validators.email]),
        numberOfChildren: new FormControl(null, [Validators.required]),
        group: new FormControl([Validators.required])
     });

    
    }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.dialogRef.updateSize('25%', '75%');
  }

}
