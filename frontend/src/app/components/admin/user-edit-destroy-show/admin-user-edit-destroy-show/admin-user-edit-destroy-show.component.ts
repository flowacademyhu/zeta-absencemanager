import { Component, OnInit, Input, ViewChild, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl, MatPaginator } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminUserEditComponent } from '../../admin-user-edit/admin-user-edit.component';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/User.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';


@Component({
  selector: 'app-admin-user-edit-destroy-show',
  templateUrl: './admin-user-edit-destroy-show.component.html',
  styleUrls: ['./admin-user-edit-destroy-show.component.css']
})
export class AdminUserEditDestroyShowComponent implements OnInit, OnDestroy {

  private _unsubscribe$: Subject<boolean> = new Subject<boolean>();
  user: User;
  editedUser: User;
  users: User[];
  dataSource : any;

  @ViewChild(MatPaginator, {static: true}) paginator : MatPaginator;

  constructor(private route: ActivatedRoute ,public dialog: MatDialog, private api: ApiCommunicationService) { }

  displayedColumns: string[] = ['dateOfBirth', 'firstName', 'lastName', 'position', 'supervisor', 
                                'dateOfEndTrial', 'dateOfEntry', 'email', 'edit', 'delete'];



  ngOnInit() {
    this.route.data
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((data: any) => {
        this.users = data.userList;
       // this.showUser.push(this.users);
       // this.dataSource = new MatTableDataSource(this.showUser); 
        this.dataSource = new MatTableDataSource(this.users);
        console.log(this.users);
        this.dataSource.paginator = this.paginator;
      });
  }

  
  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  editUser(id: number): void{
    const dialogRef = this.dialog.open(AdminUserEditComponent, {
      data: {user: this.users.filter(user => user.id === id)[0]}
    });

    dialogRef.afterClosed().pipe(takeUntil(this._unsubscribe$)).subscribe(result => {
      this.editedUser = this.users.filter(user => user.id === id)[0];
      Object.assign(this.editedUser, result);
      this.editedUser.id = id;
      console.log(this.editedUser);      
      this.api.user().updateUser(this.editedUser.id, this.editedUser).subscribe(u => console.log("updated:" + u));
    });
  }
}

