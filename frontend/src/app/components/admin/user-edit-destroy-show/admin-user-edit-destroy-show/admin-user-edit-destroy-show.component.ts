import { Component, OnInit, Input, ViewChild, OnDestroy } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl, MatPaginator } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AdminUserEditComponent } from '../../admin-user-edit/admin-user-edit.component';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/User.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';


@Component({
  selector: 'app-admin-user-edit-destroy-show',
  templateUrl: './admin-user-edit-destroy-show.component.html',
  styleUrls: ['./admin-user-edit-destroy-show.component.css']
})
export class AdminUserEditDestroyShowComponent implements OnInit, OnDestroy {

  private _unsubscribe$: Subject<boolean> = new Subject<boolean>();

  users: User[];
  showUser: User [] = [];
  // user : User[];  
  dataSource : any;

  @ViewChild(MatPaginator, {static: true}) paginator : MatPaginator;

  constructor(private route: ActivatedRoute ,public dialog: MatDialog) { }

  displayedColumns: string[] = ['dateOfBirth', 'firstName', 'lastName', 'position', 'supervisor', 
                                'dateOfEndTrial', 'dateOfEntry', 'email', 'edit', 'delete'];

  openDialog(){
    this.dialog.open(AdminUserEditComponent);
  }

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
}

