import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormsModule, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-employee-profile-edit',
  templateUrl: './employee-profile-edit.component.html',
  styleUrls: ['./employee-profile-edit.component.css']
})
export class EmployeeProfileEditComponent implements OnInit {

  profileForm  = this.formbuild.group({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    email: new FormControl(''),
  });

  
  constructor(private formbuild: FormBuilder) { }

  ngOnInit() {
  }


  updateProfile() {
    this.profileForm.patchValue({
      firstName: 'Nancy',
    lastName: 'Pista', 
    email: 'a@b.com'
      
      
    });
  }
 

}
