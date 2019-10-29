import { Component, OnInit, OnDestroy } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdminUserAddModalComponent } from 'src/app/components/admin/modals/admin-user-add-modal/admin-user-add-modal.component';
import { User } from 'src/app/models/User.model';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AdminUserEditModalComponent } from '../modals/admin-user-edit-modal/admin-user-edit-modal.component';
import { MatTableDataSource } from '@angular/material';
import { AdminUserDeleteModalComponent } from '../modals/admin-user-delete-modal/admin-user-delete-modal.component';



@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['name', 'dob', 'position', 'supervisor', 'doe', 'email', 'edit', 'delete'];
  dataSource: any; // --> filter

  editedUser: User;
  userData: User;
  usersList: User[];
  error: string;
  private _unsubscribe$ = new Subject<void>();

  constructor(
    private api: ApiCommunicationService,
    public dialog: MatDialog,
  ) { }


  ngOnInit() {
    this.api.user().getUsers().subscribe(users => {
      this.usersList = users;
      this.dataSource = new MatTableDataSource(this.usersList);
    })
    this.api.employee().getCurrent().subscribe(currentUser => {
      this.userData = currentUser;
    });
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  createUser(): void {
    const dialogRef = this.dialog.open(AdminUserAddModalComponent, {});

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((result: User) => {
        console.log(result);
        this.api
          .user()
          .createUser(result)
          .subscribe(u => {
            this.api
              .user()
              .getUsers()
              .subscribe(data => {
                this.dataSource = data;
              });
          });
      });
  }

  private dateConverter() {
    this.dataSource.dateOfEndTrial = (this.dataSource.dateOfEndTrial as Date).toISOString().split("T")[0].split("-");
    this.dataSource.dateOfBirth = (this.dataSource.dateOfBirth as Date).toISOString().split("T")[0].split("-");
    this.dataSource.dateOfEntry = (this.dataSource.dateOfEntry as Date).toISOString().split("T")[0].split("-");
    for (let i = 0; i < 3; i++) {
      this.dataSource.dateOfEntry[i] = parseInt(this.dataSource.dateOfEntry[i]);
    }
    for (let i = 0; i < 3; i++) {
      this.dataSource.dateOfBirth[i] = parseInt(this.dataSource.dateOfBirth[i]);
    }
    for (let i = 0; i < 3; i++) {
      this.dataSource.dateOfEndTrial[i] = parseInt(this.dataSource.dateOfEndTrial[i]);
    }
  }

  editUser(id: number): void {
    const dialogRef = this.dialog.open(AdminUserEditModalComponent, {
      data: { user: this.usersList.filter(user => user.id === id)[0] }
    });

    dialogRef.afterClosed().pipe(takeUntil(this._unsubscribe$)).subscribe(result => {
      this.editedUser = this.usersList.filter(user => user.id === id)[0];
      Object.assign(this.editedUser, result);
      this.editedUser.id = id;
      console.log(this.editedUser);
      this.api.user().updateUser(this.editedUser.id, this.editedUser).subscribe(u => console.log("updated:" + u));
    });
  }


  deleteUser(id: number): void {
    console.log("ID - component: " + id);
    const dialogRef = this.dialog.open(AdminUserDeleteModalComponent, {
      data: { user: this.usersList.filter(user => user.id === id)[0] }
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(result => {
        if (result === true) {
          this.api
            .user()
            .deleteUser(id)
            .subscribe(u => {
              this.api
                .user()
                .getUsers()
                .subscribe(data => {
                  this.dataSource = data;
                });
            });
        }
      });
  }

}
