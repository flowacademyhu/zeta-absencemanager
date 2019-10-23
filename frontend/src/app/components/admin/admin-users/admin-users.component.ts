import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AdminUserAddModalComponent } from 'src/app/components/admin/modals/admin-user-add-modal/admin-user-add-modal.component';
import { User } from 'src/app/models/User.model';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AdminUserEditModalComponent } from '../modals/admin-user-edit-modal/admin-user-edit-modal.component';
import { MatTableDataSource } from '@angular/material';



@Component({
  selector: 'app-admin-users',
  templateUrl: './admin-users.component.html',  
  styleUrls: ['./admin-users.component.css']
})
export class AdminUsersComponent implements OnInit {
  displayedColumns: string[] = ['name', 'dob', 'position', 'supervisor', 'doe', 'email', 'edit', 'delete'];
  dataSource; // --> filter

  editedUser: User;
  userData: User;
  usersList: User[];
  private unsubscribe$ = new Subject<void>();



  constructor(private api: ApiCommunicationService, public dialog: MatDialog) {}

  createUser(): void {
    const dialogRef = this.dialog.open(AdminUserAddModalComponent, {
      data: {user: this.userData}
    });

    dialogRef.afterClosed().pipe(takeUntil(this.unsubscribe$)).subscribe(result => {
      if(result){
        this.userData = result;
        this.userData.isOnTrial = true;
        this.userData.role = "EMPLOYEE";
        this.dateConverter();
        console.log(this.userData);      
        this.api.user().createUser(this.userData).subscribe(u => console.log("created:" + u));
        this.api.user().getUsers().subscribe(users => {this.usersList = users, this.dataSource = new MatTableDataSource(this.usersList)});
      }
    });
    
  }

  ngOnInit() {
    this.api.user().getUsers().subscribe(users => {
      this.usersList = users;
      this.dataSource = new MatTableDataSource(this.usersList);
    })
  }
  
  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete(); 
  }

  private dateConverter() {
    this.userData.dateOfEndTrial = (this.userData.dateOfEndTrial as Date).toISOString().split("T")[0].split("-");
    this.userData.dateOfBirth = (this.userData.dateOfBirth as Date).toISOString().split("T")[0].split("-");
    this.userData.dateOfEntry = (this.userData.dateOfEntry as Date).toISOString().split("T")[0].split("-"); 
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfEntry[i] = parseInt(this.userData.dateOfEntry[i]);
    }
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfBirth[i] = parseInt(this.userData.dateOfBirth[i]);
    }
    for (let i = 0; i < 3; i++) {
      this.userData.dateOfEndTrial[i] = parseInt(this.userData.dateOfEndTrial[i]);
    }
  }

  editUser(id: number): void{
    const dialogRef = this.dialog.open(AdminUserEditModalComponent, {
      data: {user: this.usersList.filter(user => user.id === id)[0]}
    });

    dialogRef.afterClosed().pipe(takeUntil(this.unsubscribe$)).subscribe(result => {
      this.editedUser = this.usersList.filter(user => user.id === id)[0];
      Object.assign(this.editedUser, result);
      this.editedUser.id = id;
      console.log(this.editedUser);      
      this.api.user().updateUser(this.editedUser.id, this.editedUser).subscribe(u => console.log("updated:" + u));
    });
  }
  
}