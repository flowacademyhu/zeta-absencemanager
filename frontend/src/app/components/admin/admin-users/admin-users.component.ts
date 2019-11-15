import { Component, OnInit, OnDestroy } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { AdminUserAddModalComponent } from "src/app/components/admin/modals/admin-user-add-modal/admin-user-add-modal.component";
import { User, UserFilter, Role } from "src/app/models/User.model";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { AdminUserEditModalComponent } from "../modals/admin-user-edit-modal/admin-user-edit-modal.component";
import { MatTableDataSource, PageEvent } from "@angular/material";
import { AdminUserDeleteModalComponent } from "../modals/admin-user-delete-modal/admin-user-delete-modal.component";
import { Router, ActivatedRoute } from "@angular/router";
import { Absence } from "src/app/models/Absence.model";

@Component({
  selector: "app-admin-users",
  templateUrl: "./admin-users.component.html",
  styleUrls: ["./admin-users.component.css"]
})
export class AdminUsersComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = [
    "name",
    "dob",
    "position",
    "supervisor",
    "doe",
    "email",
    "edit",
    "delete"
  ];
  filterColumns: string[] = [
    "name",
    "dateOfEntryStart",
    "dateOfEntryFinish",
    "dateOfEndTrialStart",
    "dateOfEndTrialFinish",
    "group",
    "position",
    "role"
  ];
  dataSource: any; // --> filter

  editedUser: User;
  userData: User;
  usersList: User[];
  error: string;
  public length = 0;
  public pageIndex = 0;
  public pageSize = 5;
  private checkedFilter: false;
  private userFilter: UserFilter = new UserFilter();
  private _unsubscribe$ = new Subject<void>();
  private groups;
  private roles;

  constructor(
    private api: ApiCommunicationService,
    private activatedRoute: ActivatedRoute,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.data.pipe(takeUntil(this._unsubscribe$)).subscribe(data => {
      this.usersList = data.userList.embedded;
      this.pageSize = data.userList.metadata.pageSize;
      this.pageIndex = data.userList.metadata.pageIndex;
      this.length = data.userList.metadata.totalElements;
      this.dataSource = new MatTableDataSource(this.usersList);
    });
    this.api
      .employee()
      .getCurrent()
      .subscribe(currentUser => {
        this.userData = currentUser;
      });
    this.api
      .group()
      .getGroups()
      .subscribe(g => {
        this.groups = g;
      });
    this.roles = User.enumSelector(Role);
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
        if (result != undefined) {
          result.dateOfBirth = Absence.convertDate(result.dateOfBirth);
          result.dateOfEntry = Absence.convertDate(result.dateOfEntry);
          result.dateOfEndTrial = Absence.convertDate(result.dateOfEndTrial);
          this.api
            .user()
            .createUser(result)
            .subscribe(() => this.router.navigateByUrl(this.router.url));
        }
      });
  }

  editUser(id: number): void {
    const dialogRef = this.dialog.open(AdminUserEditModalComponent, {
      data: { user: this.usersList.filter(user => user.id === id)[0] }
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(result => {
        if (result != undefined) {
          this.editedUser = this.usersList.filter(user => user.id === id)[0];
          result.dateOfBirth = Absence.convertDate(result.dateOfBirth);
          result.dateOfEntry = Absence.convertDate(result.dateOfEntry);
          result.dateOfEndTrial = Absence.convertDate(result.dateOfEndTrial);
          Object.assign(this.editedUser, result);
          this.editedUser.id = id;
          this.api
            .user()
            .updateUser(this.editedUser.id, this.editedUser)
            .subscribe(() => this.router.navigateByUrl(this.router.url));
        }
      });
  }

  deleteUser(id: number): void {
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
            .subscribe(() => this.router.navigateByUrl(this.router.url));
        }
      });
  }

  public onPageChange(event: PageEvent) {
    this.router.navigate(["admin", "users"], {
      queryParams: {
        page: event.pageIndex,
        size: event.pageSize
      }
    });
  }

  public onFilterReset(checked: boolean) {
    if (!checked) {
      this.userFilter = new UserFilter();
      this.router.navigate(["admin", "users"]);
    }
  }

  public onFilter() {
    console.log(this.userFilter.dateOfEntryStart);
    this.router.navigate(["admin", "users"], {
      queryParams: this.userFilter
    });
  }
}
