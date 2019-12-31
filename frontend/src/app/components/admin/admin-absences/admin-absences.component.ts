import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  OnDestroy
} from "@angular/core";
import {
  Absence,
  AbsenceType,
  Status,
  AbsencesFilter
} from "src/app/models/Absence.model";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Subject } from "rxjs";
import { takeUntil, debounceTime } from "rxjs/operators";
import { MatDialogConfig, MatDialog } from "@angular/material/dialog";
import { AdminAbsenceCreateModalComponent } from "../modals/admin-absence-create-modal/admin-absence-create-modal.component";
import { AdminAbsenceEditModalComponent } from "../modals/admin-absence-edit-modal/admin-absence-edit-modal.component";
import { User } from "src/app/models/User.model";
import { SessionService } from "src/app/services/session.service";
import * as cloneDeep from "lodash/cloneDeep";
import { MatPaginator, PageEvent } from "@angular/material";
import { ExcelService } from "src/app/services/excel.service";
import { DateFormingService } from "src/app/services/date-forming.service";

@Component({
  selector: "app-admin-absences",
  templateUrl: "./admin-absences.component.html",
  styleUrls: ["./admin-absences.component.css"]
})
export class AdminAbsencesComponent implements OnInit, OnDestroy {
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
  filterColumns: string[] = [
    "administrationID",
    "start",
    "finish",
    "dayStart",
    "dayEnd",
    "type",
    "status",
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
  public absenceStatus: Status[] = [
    null,
    Status.OPEN,
    Status.UNDER_REVIEW,
    Status.APPROVED,
    Status.REJECTED,
    Status.ADMINISTRATED,
    Status.DONE
  ];
  public absenceType: AbsenceType[] = [
    null,
    AbsenceType.ABSENCE,
    AbsenceType.CHILD_SICK_PAY,
    AbsenceType.NON_WORKING,
    AbsenceType.UNPAID_HOLIDAY
  ];

  checkedFilter: false;
  absenceFilter: AbsencesFilter = new AbsencesFilter();
  private stringFilter = new Subject();
  public stringFilter$ = this.stringFilter.asObservable();

  constructor(
    public api: ApiCommunicationService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private router: Router,
    private session: SessionService,
    private excelService: ExcelService,
    private dateFormingService: DateFormingService
  ) {}

  ngOnInit() {
    this.route.data.pipe(takeUntil(this._unsubscribe$)).subscribe(data => {
      this.absencesList = data.adminAbsenceList.embedded;
      this.pageSize = data.adminAbsenceList.metadata.pageSize;
      this.pageIndex = data.adminAbsenceList.metadata.pageIndex;
      this.length = data.adminAbsenceList.metadata.totalElements;
    });
    this.stringFilter$
      .pipe(debounceTime(500), takeUntil(this._unsubscribe$))
      .subscribe(() => this.onFilter());
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
    });
  }

  public onFilter() {
    console.log(this.absenceFilter);
    this.router.navigate(["admin", "absences"], {
      queryParams: this.absenceFilter
    });
  }

  public onFilterReset(checked: boolean) {
    if (!checked) {
      this.absenceFilter = new AbsencesFilter();
      this.router.navigate(["admin", "absences"]);
    }
  }

  exportAsXLSX(): void {
    const list: any = cloneDeep(this.absencesList);
    list.forEach(absence => {
      absence.reporter =
        absence.reporter.lastName + " " + absence.reporter.firstName;
      absence.assignee =
        absence.assignee.lastName + " " + absence.assignee.firstName;
      absence.begin = absence.begin.toString();
      absence.end = absence.end.toString();
      delete absence.createdBy;
      delete absence.updatedBy;
      delete absence.deletedBy;
      delete absence.createdAt;
      delete absence.updatedAt;
      delete absence.deletedAt;
    });
    console.log(this.absencesList);
    console.log(list);
    this.excelService.exportAsExcelFile(list, "sample");
  }
}
