import { Component, OnInit, ViewChild, AfterViewInit } from "@angular/core";
import { Absence } from "src/app/models/Absence.model";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Subject } from "rxjs";
import { takeUntil } from "rxjs/operators";
import { MatDialogConfig, MatDialog } from "@angular/material/dialog";
import { AdminAbsenceCreateModalComponent } from "../modals/admin-absence-create-modal/admin-absence-create-modal.component";
import { AdminAbsenceEditModalComponent } from "../modals/admin-absence-edit-modal/admin-absence-edit-modal.component";
import { User } from 'src/app/models/User.model';
import { SessionService } from 'src/app/services/session.service';
import { MatPaginator, PageEvent } from '@angular/material';

@Component({
  selector: "app-admin-absences",
  templateUrl: "./admin-absences.component.html",
  styleUrls: ["./admin-absences.component.css"]
})
export class AdminAbsencesComponent implements OnInit {
  private _unsubscribe$: Subject<boolean> = new Subject<boolean>();
  displayedColumns: string[] = [
    "id",
    "begin",
    "end",
    "days",
    "type",
    "status",
    "created_at",
    "reporter",
    "assignee",
    "edit"
  ];
  public absencesList: Absence[];
  private user: any;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  public length = 0;
  public pageIndex = 0;
  public pageSize = 5; 

  constructor(
    public api: ApiCommunicationService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private router: Router,
    private session: SessionService
  ) {}

  ngOnInit() {
    this.route.data
    .pipe(takeUntil(this._unsubscribe$))
    .subscribe(data => {
      console.log(data);
      this.absencesList = data.adminAbsenceList.embedded;
      this.pageSize = data.adminAbsenceList.metadata.pageSize;
      this.pageIndex = data.adminAbsenceList.metadata.pageIndex;
      this.length = data.adminAbsenceList.metadata.totalElements;
    });
  }


  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40vw";

    dialogConfig.data = {};

    const dialogRef = this.dialog.open(
      AdminAbsenceCreateModalComponent,
      dialogConfig
    );

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(() => this.router.navigateByUrl(this.router.url));
  }

  editAbsence(id: number): void {
    const dialogRef = this.dialog.open(AdminAbsenceEditModalComponent, {
      data: {
        absence: this.absencesList.filter(absence => absence.id === id)[0]
      }
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(() => this.router.navigateByUrl(this.router.url));
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  public currentUser() {
    this.user = this.session.getUserData();
  }

  public onPageChange(event: PageEvent) {
    this.router.navigate(["admin", "absences"], {
      queryParams: { 
        page: event.pageIndex,
        size: event.pageSize
      }
    })
  }
}
