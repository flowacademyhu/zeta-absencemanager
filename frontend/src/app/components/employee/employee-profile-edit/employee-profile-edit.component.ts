import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormsModule, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/models/User.model';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';



@Component({
  selector: 'app-employee-profile-edit',
  templateUrl: './employee-profile-edit.component.html',
  styleUrls: ['./employee-profile-edit.component.css']
})
export class EmployeeProfileEditComponent implements OnInit {
  private _unsubscribe$: Subject<boolean> = new Subject();
  public user: User;
  

  profileForm  = this.formbuild.group({
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    email: new FormControl(''),
  });

  
  constructor(private userService: UserService,  private formbuild: FormBuilder, 
    private route: ActivatedRoute, private api: ApiCommunicationService) { }

  ngOnInit() {
    this.route.data
    .pipe(takeUntil(this._unsubscribe$))
    .subscribe((data: any) => {
      this.user = data.user;
      console.log(this.user);
      this.profileForm.patchValue(this.user)
 
    }) 

    this.showuser();
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
    
  }

  showuser(){
    this.api.employee().getCurrent().subscribe((user: User) => {
    this.user = user;
      })
        
   }

  /*update(id: number) {
    this.api.employee().updateCurrent().subscribe((user: User) => {
      this.profileForm = this.profileForm;
        })
    
    console.log(this.profileForm)
  }
  */

 
  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.profileForm.value);
    
  }
  

}
