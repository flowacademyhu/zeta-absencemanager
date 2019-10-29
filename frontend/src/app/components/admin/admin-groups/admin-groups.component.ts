import { Component, OnInit, OnDestroy } from "@angular/core";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Group } from "src/app/models/Group.model";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { MatDialog } from "@angular/material/dialog";
import { AdminGroupCreateModalComponent } from "src/app/components/admin/modals/admin-group-create-modal/admin-group-create-modal.component";
import { AdminGroupDeleteModalComponent } from '../modals/admin-group-delete-modal/admin-group-delete-modal.component';
import { DataEntity } from 'src/app/models/DataEntity.model';

@Component({
  selector: "app-admin-groups",
  templateUrl: "./admin-groups.component.html",
  styleUrls: ["./admin-groups.component.css"]
})
export class AdminGroupsComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ["name", "parent", "leaders", "employees", "delete"];
  dataSource: any;
  error: string;
  private _unsubscribe$ = new Subject<void>();
  groupData: Group;
  groupsList: Group[];

  constructor(
    private api: ApiCommunicationService,
    private activatedRoute: ActivatedRoute,
    public dialog: MatDialog,
    public router: Router
  ) {
    this.activatedRoute.data.pipe(takeUntil(this._unsubscribe$)).subscribe(
      data => {
        this.dataSource = data.groupResolver;
        this.transformData();
      },
      error => {
        this.error = error;
      }
    );
  }

  transformData() {
    this.dataSource.forEach(element => {
      if (element.parentId) {
        this.dataSource.forEach(group => {
          if (element.parentId === group.id) {
            element.parent = group.name;
          }
        });
      }
    });
  }

  ngOnInit() {
    this.api.group().getGroups().subscribe(users => {
      this.groupsList = users;
    })
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  createGroup(): void {
    const dialogRef = this.dialog.open(AdminGroupCreateModalComponent, {});

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((result: Group) => {
        console.log(result);
        result.leader = <DataEntity>{id: result.leaderId};
        this.api
          .group()
          .createGroup(result)
          .subscribe(() => this.router.navigateByUrl(this.router.url));
      });
  }

  deleteGroup(id: number): void {
    console.log("ID - component: " + id);
    const dialogRef = this.dialog.open(AdminGroupDeleteModalComponent, {
      data: {group: this.groupsList.filter(group => group.id === id)[0]}
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(result => {
        if(result === true) {
          this.api
            .group()
            .deleteGroup(id)
            .subscribe(() => this.router.navigateByUrl(this.router.url));
      }});
  } 

}
