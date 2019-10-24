import { Component, OnInit } from '@angular/core';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { ActivatedRoute } from '@angular/router';
import { Group } from 'src/app/models/Group.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { MatTableDataSource, MatFormFieldControl } from '@angular/material';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AdminGroupCreateModalComponent } from 'src/app/components/admin/modals/admin-group-create-modal/admin-group-create-modal.component';
import { User } from 'src/app/models/User.model';

@Component({
  selector: 'app-admin-groups',
  templateUrl: './admin-groups.component.html',
  styleUrls: ['./admin-groups.component.css']
})
export class AdminGroupsComponent implements OnInit {
  displayedColumns: string[] = ['name', 'parent', 'leaders', 'employees'];
  dataSource: any;
  error: string;

  groupData: Group;
  private unsubscribe$ = new Subject<void>();

  constructor(private api: ApiCommunicationService, private activatedRoute: ActivatedRoute, public dialog: MatDialog) {
    this.activatedRoute.data.pipe(takeUntil(this.unsubscribe$)).subscribe((data) => {
      this.dataSource = data.groupResolver;
      console.log(this.dataSource)
      this.dataSource.forEach(element => {
        if (element.parentId !== null) {
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

  createGroup(): void {
    const dialogRef = this.dialog.open(AdminGroupCreateModalComponent, {

    });

    dialogRef.afterClosed().pipe(takeUntil(this.unsubscribe$)).subscribe((result: Group) => {
      console.log(result);     
      this.api.group().createGroup(result).subscribe(u => console.log("created:" + u));
      this.api.group().getGroups().subscribe((data) => {
        this.dataSource = data;
      })
    });
    
  }
}
