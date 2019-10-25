import { Component, OnInit, OnDestroy } from '@angular/core';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { ActivatedRoute } from '@angular/router';
import { Group } from 'src/app/models/Group.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { MatTableDataSource, MatFormFieldControl } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AdminGroupCreateModalComponent } from 'src/app/components/admin/modals/admin-group-create-modal/admin-group-create-modal.component';
import { User } from 'src/app/models/User.model';
import { AdminGroupEditModalComponent } from '../modals/admin-group-edit-modal/admin-group-edit-modal.component';

@Component({
  selector: 'app-admin-groups',
  templateUrl: './admin-groups.component.html',
  styleUrls: ['./admin-groups.component.css']
})
export class AdminGroupsComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['name', 'parent', 'leaders', 'employees', 'edit'];
  dataSource: any;
  error: string;
  private _unsubscribe$ = new Subject<void>();
  groupData: Group;
  editedGroup: Group;

  constructor(private api: ApiCommunicationService, private activatedRoute: ActivatedRoute, public dialog: MatDialog) {
    this.activatedRoute.data.pipe(takeUntil(this._unsubscribe$)).subscribe((data) => {
      this.dataSource = data.groupResolver;
      this.dataSource.forEach(element => {
        if (element.parentId) {
          this.dataSource.forEach(group => {
            if (element.parentId === group.id) {
              element.parent = group.name;
            }
          });
        }
      });
    }, (error) => {
      this.error = error;
    })
  }

  ngOnInit() { }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete(); 
  }

  createGroup(): void {
    const dialogRef = this.dialog.open(AdminGroupCreateModalComponent, {

    });

    dialogRef.afterClosed().pipe(takeUntil(this._unsubscribe$)).subscribe((result: Group) => {
      console.log(result);     
      this.api.group().createGroup(result).subscribe(u => console.log("created:" + u));
      this.api.group().getGroups().subscribe((data) => {
        this.dataSource = data;
      })
    });
    
  }

  editGroup(id: number): void {
    const dialogRef = this.dialog.open(AdminGroupEditModalComponent, {
      data: {group: this.dataSource.filter(group => group.id === id)[0]}
    });
    
    dialogRef.afterClosed().pipe(takeUntil(this._unsubscribe$)).subscribe(result => {
      this.editedGroup = this.dataSource.filter(group => group.id === id)[0];
      Object.assign(this.editedGroup, result);
      this.editedGroup.id = id;
      console.log(this.editedGroup);      
      this.api.group().updateGroup(this.editedGroup.id, this.editedGroup).subscribe(u => console.log("updated:" + u));
    });
  }
}
