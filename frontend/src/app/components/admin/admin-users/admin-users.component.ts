import { Component, OnInit, OnDestroy } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { AdminUserAddModalComponent } from "src/app/components/admin/modals/admin-user-add-modal/admin-user-add-modal.component";
import { User, Role } from "src/app/models/User.model";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { AdminUserEditModalComponent } from "../modals/admin-user-edit-modal/admin-user-edit-modal.component";
import { MatTableDataSource, PageEvent, Sort } from "@angular/material";
import { AdminUserDeleteModalComponent } from "../modals/admin-user-delete-modal/admin-user-delete-modal.component";
import { Router, ActivatedRoute } from "@angular/router";
import { Absence } from "src/app/models/Absence.model";
import { UsersPagedRequest } from "src/app/models/UsersPagedRequest";

@Component({
  selector: "app-admin-users",
  templateUrl: "./admin-users.component.html",
  styleUrls: ["./admin-users.component.css"]
})
export class AdminUsersComponent implements OnInit, OnDestroy {
  private displayedColumns: string[] = [
    "name",
    "dob",
    "position",
    "supervisor",
    "doe",
    "email",
    "szerep",
    "edit",
    "delete"
  ];

  private editedUser: User;
  private userData: User;
  private usersList: User[];
  private error: string;
  public length = 0;
  public pageIndex = 0;
  public pageSize = 5;
  private checkedFilter: false;
  private usersPagedRequest = new UsersPagedRequest();
  private _unsubscribe$ = new Subject<void>();
  private groups;
  private roles;

  constructor(
    private api: ApiCommunicationService,
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

  public createUser(): void {
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

  public editUser(id: number): void {
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

  public deleteUser(id: number): void {
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

  public onPageChange(event: PageEvent): void {
    this.addDataToUsersPagedRequest(event, undefined);
    this.router.navigate(["admin", "users"], {
      queryParams: this.usersPagedRequest
    });
  }

  public onFilterReset(checked: boolean): void {
    if (!checked) {
      this.clearFilterData();
      this.router.navigate(["admin", "users"], {
        queryParams: this.usersPagedRequest
      });
    }
  }

  public onFilter(): void {
    this.clearEmptyFilter();
    this.router.navigate(["admin", "users"], {
      queryParams: this.usersPagedRequest
    });
  }

  public sortData(sort: Sort): void {
    this.addDataToUsersPagedRequest(undefined, sort);
    this.router.navigate(["admin", "users"], {
      queryParams: this.usersPagedRequest
    });
  }

  public addDataToUsersPagedRequest(event?: PageEvent, sort?: Sort): void {
    if (event != undefined) {
      this.usersPagedRequest.page = event.pageIndex;
      this.usersPagedRequest.size = event.pageSize;
    }
    if (sort != undefined) {
      if (sort.direction == "") {
        this.usersPagedRequest.sort = undefined;
      } else {
        this.usersPagedRequest.sort = sort.active + "," + sort.direction;
      }
    }
  }

  public addRoleToRoles(checked: boolean, role: Role): void {
    if (checked) {
      if (this.usersPagedRequest.role == undefined) {
        this.usersPagedRequest.role = [];
      }
      this.usersPagedRequest.role.push(role);
      this.onFilter();
    } else {
      this.usersPagedRequest.role = this.usersPagedRequest.role.filter(
        r => r != role
      );
      if (this.usersPagedRequest.role.length == 0) {
        this.usersPagedRequest.role = undefined;
      }
      this.onFilter();
    }
  }

  public isInRoles(role: Role): boolean {
    return (
      this.usersPagedRequest.role != undefined &&
      this.usersPagedRequest.role.includes(role)
    );
  }

  public clearFilterData(): void {
    this.usersPagedRequest.name = undefined;
    this.usersPagedRequest.dateOfEntryStart = undefined;
    this.usersPagedRequest.dateOfEntryFinish = undefined;
    this.usersPagedRequest.dateOfEndTrialStart = undefined;
    this.usersPagedRequest.dateOfEndTrialFinish = undefined;
    this.usersPagedRequest.group = undefined;
    this.usersPagedRequest.position = undefined;
    this.usersPagedRequest.role = undefined;
  }

  public clearEmptyFilter(): void {
    if (this.usersPagedRequest.name == "") {
      this.usersPagedRequest.name = undefined;
    }
    if (this.usersPagedRequest.dateOfEntryStart == "") {
      this.usersPagedRequest.dateOfEntryStart = undefined;
    }
    if (this.usersPagedRequest.dateOfEntryFinish == "") {
      this.usersPagedRequest.dateOfEntryFinish = undefined;
    }
    if (this.usersPagedRequest.dateOfEndTrialStart == "") {
      this.usersPagedRequest.dateOfEndTrialStart = undefined;
    }
    if (this.usersPagedRequest.dateOfEndTrialFinish == "") {
      this.usersPagedRequest.dateOfEndTrialFinish = undefined;
    }
    if (this.usersPagedRequest.position == "") {
      this.usersPagedRequest.position = undefined;
    }
  }
}
