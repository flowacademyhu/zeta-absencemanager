import { Component, OnInit, OnDestroy } from "@angular/core";
import {
  FormGroup,
  FormControl,
  FormsModule,
  FormBuilder,
  Validators
} from "@angular/forms";
import { User } from "src/app/models/User.model";
import { CommonModule } from "@angular/common";
import { ActivatedRoute } from "@angular/router";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { UserService } from "src/app/services/user.service";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { MatDialog } from "@angular/material/dialog";
import { ChangePasswModalComponent } from "../../employee/modals/change-passw-modal/change-passw-modal.component";
import { EmployeeProfileDeleteModalComponent } from '../modals/employee-profile-delete-modal/employee-profile-delete-modal.component';
import { SessionService } from 'src/app/services/session.service';
import { EmployeeProfileEditModalComponent } from '../modals/employee-profile-edit-modal/employee-profile-edit-modal.component';
import { Absence } from 'src/app/models/Absence.model';

@Component({
  selector: "app-employee-profile",
  templateUrl: "./employee-profile.component.html",
  styleUrls: ["./employee-profile.component.css"]
})
export class EmployeeProfileComponent implements OnInit, OnDestroy {
  private _unsubscribe$: Subject<boolean> = new Subject();
  public user: User;

  constructor(
    private userService: UserService,
    private formbuild: FormBuilder,
    private route: ActivatedRoute,
    private api: ApiCommunicationService,
    private sessionService: SessionService,
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.api
      .employee()
      .getCurrent()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((user: User) => this.user = user);
    }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  changePassw() {
    this.dialog.open(ChangePasswModalComponent);
  }

  deleteProfile(): void {
    const dialogRef = this.dialog.open(EmployeeProfileDeleteModalComponent, { 
      data: { user: this.user }
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(result => {
        if (result === true) {
          this.api
            .employee()
            .deleteProfile(this.user.id)
            .subscribe(() => {});
          this.sessionService.logout();
        }
      });
  }
  
  openEditProfile() {
    const dialogRef = this.dialog.open(EmployeeProfileEditModalComponent, {
      data: {user: this.user}
    });
    dialogRef.afterClosed().pipe(takeUntil(this._unsubscribe$)).subscribe(result => {
      if(result){
      result.dateOfBirth = Absence.convertDate(result.dateOfBirth);
      }
      Object.assign(this.user, result);      
      this.api.user().updateSelfUser(this.user.id, this.user).subscribe();
    });
  }
}
