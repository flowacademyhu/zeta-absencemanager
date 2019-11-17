import { Component, OnInit, OnDestroy } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { AdminUserAddModalComponent } from "src/app/components/admin/modals/admin-user-add-modal/admin-user-add-modal.component";
import { User, Role } from "src/app/models/User.model";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { AdminUserEditModalComponent } from "../modals/admin-user-edit-modal/admin-user-edit-modal.component";
import { MatTableDataSource } from "@angular/material";
import { AdminUserDeleteModalComponent } from "../modals/admin-user-delete-modal/admin-user-delete-modal.component";
import { Router, ActivatedRoute } from "@angular/router";
import { Absence } from "src/app/models/Absence.model";

@Component({
  selector: 'app-admin-deleted-users',
  templateUrl: './admin-deleted-users.component.html',
  styleUrls: ['./admin-deleted-users.component.css']
})
export class AdminDeletedUsersComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = [
    "name",
    "dob",
    "position",
    "doe",
    "email",
    "deletedAt",
    "role"
  ];
  dataSource: any; // --> filter

  userData: User;
  usersList: User[];
  error: string;
  private _unsubscribe$ = new Subject<void>();

  constructor(
    private api: ApiCommunicationService,
    private activatedRoute: ActivatedRoute,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.pipe(takeUntil(this._unsubscribe$)).subscribe(data => {
      this.usersList = data.userList;
      console.log(data);
      this.dataSource = new MatTableDataSource(this.usersList);
    });
    this.api
      .employee()
      .getCurrent()
      .subscribe(currentUser => {
        this.userData = currentUser;
      });
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

}

