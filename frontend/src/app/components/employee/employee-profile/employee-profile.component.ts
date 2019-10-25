import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormGroup, FormControl, FormsModule, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/models/User.model';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswComponent } from 'src/app/components/employee/modals/change-passw/change-passw.component';


@Component({
  selector: 'app-employee-profile',
  templateUrl: './employee-profile.component.html',
  styleUrls: ['./employee-profile.component.css']
})
export class EmployeeProfileComponent implements OnInit, OnDestroy {
    private _unsubscribe$: Subject<boolean> = new Subject();
    public user: User;

  constructor(private userService: UserService,  
              private formbuild: FormBuilder, 
              private route: ActivatedRoute, 
              private api: ApiCommunicationService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.api.employee().getCurrent()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((user: User) => this.user = user);
}

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  changePassw(){
    this.dialog.open(ChangePasswComponent)
  }

}

  







  
  


  

  

