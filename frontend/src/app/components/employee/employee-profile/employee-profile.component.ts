import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormsModule, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/models/User.model';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';


@Component({
  selector: 'app-employee-profile',
  templateUrl: './employee-profile.component.html',
  styleUrls: ['./employee-profile.component.css']
})
export class EmployeeProfileComponent implements OnInit {
  private _unsubscribe$: Subject<boolean> = new Subject();
  private user: User;
  private editprofile: FormGroup = new FormGroup({});
  private isedit: boolean = false;
  private buttonname: string = "EDIT"
  
constructor(private userService: UserService,  private formbuild: FormBuilder, private route: ActivatedRoute, private api: ApiCommunicationService) { }

ngOnInit() {
this.showuser();
}

ngOnDestroy(): void {
this._unsubscribe$.next();
this._unsubscribe$.complete();
}
  
showuser(){
this.api.employee().getCurrent().subscribe((user: User) => {
this.user = user;
this.editprofile = new FormGroup({
  firstName: new FormControl(this.user.firstName),
  lastName: new FormControl(this.user.lastName),
  email: new FormControl(this.user.email),
});
  })   
}

onedit(editprofileValue) {
if (this.isedit) {
  this.user.firstName = editprofileValue.firstName,
  this.user.lastName = editprofileValue.lastName,
  this.user.email = editprofileValue.email;
  this.api.employee().updateCurrent(this.user.id, this.user).subscribe(data => {});
}

this.isedit = !this.isedit;

this.buttonname = this.isedit? "SAVE":"EDIT";
}
}
  







  
  


  

  

